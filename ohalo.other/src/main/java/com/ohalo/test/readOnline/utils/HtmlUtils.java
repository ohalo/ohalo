package com.ohalo.test.readOnline.utils;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;
import org.ohalo.base.utils.LogUtils;

import com.ohalo.test.readOnline.GetInternetResouce;

/**
 * 
 * <pre>
 * ###description###
 * 
 * html 解析
 * 
 * #################
 * </pre>
 * 
 * @author Z.D.Halo
 * @since 2013-4-29
 * @version 1.0
 */
public class HtmlUtils {

  private static Log logger = LogFactory.getLog(HtmlUtils.class);

  public static Set<String> linkSet = new HashSet<String>();

  public static String[] pageArray = new String[91];

  public static ExecutorService pool = Executors.newFixedThreadPool(20);

  private static final String[] urls = new String[] { "http://www.12306.cn/" };

  public static void main(String[] args) throws ParserException, IOException {

    String path = "http://cl.man.lv/thread0806.php?fid=16";
    String page = "&search=&page=";

    for (int i = 0; i < pageArray.length; i++) {
      pageArray[i] = path + page + i;
    }

    for (final String pageUrl : pageArray) {
      Thread thread = new Thread(new Runnable() {
        public void run() {
          try {
            toGetLinkAddress(pageUrl, GetInternetResouce.i, "html");
          } catch (ParserException e) {
            return;
          }
        }
      });
      thread.start();
      GetInternetResouce.i = GetInternetResouce.i + 10000;
    }

  }

  public static void main1(String[] args) throws ParserException, IOException {

    for (int i = 0; i < urls.length; i++) {
      final String url = urls[i];
      pool.execute(new Runnable() {
        @Override
        public void run() {
          HtmlUtils.collectHrefUrl(url);
        }
      });
    }

    // pool.shutdown();
  }

  public static void collectHrefUrl(String urlAddress) {
    if (linkSet.size() > 10000) {
      linkSet.clear();
    }
    Parser parser = null;
    NodeList nodelist = null;
    try {
      URL url = new URL(urlAddress);
      parser = new Parser(url.openConnection());
      HtmlPage page = new HtmlPage(parser);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {

      }
      parser.visitAllNodesWith(page);
      nodelist = page.getBody();
      NodeFilter filter = new TagNameFilter("A");
      nodelist = nodelist.extractAllNodesThatMatch(filter, true);
      for (int i = 0; i < nodelist.size(); i++) {
        LinkTag link = (LinkTag) nodelist.elementAt(i);
        String line = link.getLink();

        if (StringUtils.isBlank(line) || !line.startsWith("http")) {
          logger.info("访问地址为：" + line);
          continue;
        }

        boolean urlflag = false;

        for (String addUrl : urls) {
          if (line.indexOf(addUrl) > -1) {
            urlflag = true;
            break;
          } else {
            urlflag = false;
          }
        }

        if (!linkSet.contains(line) && urlflag) {
          linkSet.add(line);
          LogUtils.infoMsg(
              HtmlUtils.class.getName(),
              "collectHrefUrl",
              "visitUrlAddress:" + line + "||visitUrlName:"
                  + link.getLinkText());
          System.out.println("访问地址：" + line + ",访问名称:" + link.getLinkText());
          final String line1 = line;
          pool.execute(new Runnable() {
            @Override
            public void run() {
              try {
                HtmlUtils.collectHrefUrl(line1);
              } catch (Exception e) {
                return;
              }
            }
          });
        }
      }
    } catch (ParserException e) {
      logger.error("ParserException ！", e);
      return;
    } catch (IOException e) {
      logger.error("io 读取出现异常信息！", e);
      return;
    } finally {
      parser = null;
      nodelist = null;
    }
  }

  public static void toGetLinkAddress(String addr, int x, String suffix)
      throws ParserException {
    Parser parser = null;
    NodeList nodelist = null;
    try {
      URL url = new URL(addr);
      parser = new Parser(url.openConnection());
      HtmlPage page = new HtmlPage(parser);
      parser.visitAllNodesWith(page);
      nodelist = page.getBody();
      getInputImage(nodelist, x);
    } catch (IOException e) {
      logger.error("io 读取出现异常信息！", e);
      return;
    }

    NodeFilter filter = new TagNameFilter("A");
    NodeList nodelist1 = nodelist.extractAllNodesThatMatch(filter, true);
    for (int i = 0; i < nodelist1.size(); i++) {
      LinkTag link = (LinkTag) nodelist1.elementAt(i);
      String line = link.getLink();
      if (StringUtils.endsWith(line, suffix) && !linkSet.contains(line)) {
        toGetLinkAddress(line, x++, suffix);
        linkSet.add(line);
      }
    }

  }

  /**
   * 
   * @author Z.D.Halo
   * @param nodelist
   * @throws IOException
   */
  public static void getInputImage(NodeList nodelist, int x) throws IOException {
    NodeFilter filter = new TagNameFilter("input");
    NodeList list = nodelist.extractAllNodesThatMatch(filter, true);
    for (int i = 0; i < list.size(); i++) {
      TagNode tag = (TagNode) list.elementAt(i);
      if (tag.getAttribute("type").equals("image")) {
        if (logger.isDebugEnabled()) {
          logger.debug("image http :" + tag.getAttribute("src"));
        }
        GetInternetResouce.downLoadPic(tag.getAttribute("src"), x);
      }
    }
  }

}
