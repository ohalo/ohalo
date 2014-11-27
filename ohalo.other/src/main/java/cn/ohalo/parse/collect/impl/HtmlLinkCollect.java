package cn.ohalo.parse.collect.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.ohalo.parse.buffer.LinkBuffer;
import cn.ohalo.parse.buffer.exception.BufferedInitException;
import cn.ohalo.parse.buffer.exception.BufferedStateException;
import cn.ohalo.parse.collect.Collect;
import cn.ohalo.parse.db.HtmlLinkDB;
import cn.ohalo.parse.entity.HtmlLink;

public abstract class HtmlLinkCollect implements Collect<HtmlLink> {

	public static Log logger = LogFactory.getLog(HtmlLinkCollect.class);

	private static Integer POOL_SIZE = 2;

	public LinkBuffer<HtmlLink> lbuffer;

	/**
	 * 阻塞队列元素的长度
	 */
	private int queueSize = 1;

	/**
	 * 队列中数组的长度
	 */
	private int listSize = 5000;

	/**
	 * 访问url地址
	 */
	private String _url;

	/**
	 * 
	 */
	private static Set<HtmlLink> articleCategoryLink = new HashSet<HtmlLink>();

	/**
	 * 日志集合队列
	 */
	private BlockingDeque<List<HtmlLink>> queuePool;

	/**
	 * 
	 */
	private ExecutorService executorService;

	/**
	 * 
	 * <pre>
	 * 方法体说明：初始化队列中的缓冲区 initQueues
	 * 作者：halo
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @return void
	 * 
	 * @since JDK1.6
	 */
	private void initQueues() {
		queuePool = new LinkedBlockingDeque<List<HtmlLink>>(queueSize);
		for (int i = 0; i < queueSize; i++) {
			ArrayList<HtmlLink> list = new ArrayList<HtmlLink>(listSize);
			try {
				queuePool.put(list);
			} catch (InterruptedException e) {
				throw new BufferedInitException("LogBuffer initQueues error!");
			}
		}
	}

	public HtmlLinkCollect() {
		initSender();
		initQueues();
		initThreadPool();
	}

	public void initThreadPool() {
		int cpuNums = Runtime.getRuntime().availableProcessors();
		executorService = Executors.newFixedThreadPool(cpuNums * POOL_SIZE);
	}

	public void execute(String url) {
		executorService.execute(new LinkThread(HtmlLink.class.getName(), url));
		this._url = url;
	}

	public void destory() {
		try {
			refushQueuePool();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initSender() {
		HtmlLinkDB db = HtmlLinkDB.getInstance();
		db.setCollectionName("cn.ohalo.htmlLink");
		lbuffer = new LinkBuffer<HtmlLink>();
		lbuffer.setSender(db);
	}

	public abstract HtmlLink ruleHtmlLink(Element element);

	private void refushQueuePool() throws InterruptedException {
		List<HtmlLink> links = queuePool.takeFirst();

		if (links == null || links.isEmpty()) {
			return;
		}

		for (Iterator<HtmlLink> iterator = links.iterator(); iterator.hasNext();) {
			HtmlLink htmlLink = iterator.next();
			if (StringUtils.equals(htmlLink.getType(),
					HtmlLink.ARTICLE_CATEGORY)
					&& !(articleCategoryLink.contains(htmlLink))) {
				articleCategoryLink.add(htmlLink);
				executorService.execute(new LinkThread(
						HtmlLink.class.getName(), htmlLink.getLink()));
			}
			lbuffer.write(htmlLink);
		}
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		this._url = url;
	}

	private class LinkThread extends Thread {

		private String url;

		public LinkThread(String name, String url) {
			super(name);
			this.url = url;
		}

		@Override
		public void run() {
			try {
				List<HtmlLink> links = queuePool.pollFirst();
				Document doc = Jsoup.connect(url).get();
				Elements aeles = doc.getElementsByTag("a");
				for (Iterator<Element> iterator = aeles.iterator(); iterator
						.hasNext();) {
					Element element = iterator.next();
					HtmlLink htmlLink = ruleHtmlLink(element);
					htmlLink.setMainUrlAddress(_url);
					if (null == links || links.size() == listSize) {
						queuePool.putLast(new ArrayList<HtmlLink>(listSize));
						links = queuePool.pollFirst();
					}
					links.add(htmlLink);
				}
				queuePool.putLast(links);
				refushQueuePool();
			} catch (IOException e) {
				logger.error("connection " + url + " error !", e);
			} catch (InterruptedException e) {
				throw new BufferedStateException("Link Thread is error");
			}
		}
	}
}