package cn.ohalo.stock.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import cn.ohalo.stock.entity.CompanyStock;

public class StockUtil  {

	private static Log logger = LogFactory.getLog(StockUtil.class);

	private static List<CompanyStock> cstocks = null;

	String linkaddr;
	String elementId;

	public StockUtil(String linkaddr, String elementId) {
		this.linkaddr = linkaddr;
		this.elementId = elementId;
		initJobList(linkaddr, elementId);
	}

	public List<CompanyStock> getCompanyStockList() {
		return cstocks;
	}

	private void initJobList(String linkaddr, String elementId) {
		try {
			URLConnection conn = new URL(linkaddr).openConnection();
			Parser parser = new Parser(conn);
			parser.setEncoding("GBK");
			String resultListText = getText(elementId, parser);
			margerSearchCompany(resultListText);
		} catch (ParserException e) {
			logger.error("", e);
		} catch (MalformedURLException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}

	}

	/**
	 * 获取网页标题和正文组成的文本
	 * 
	 **/
	protected String getText(String elementId, Parser parser)
			throws ParserException {
		NodeFilter TitleFilter = new NodeClassFilter(TitleTag.class);
		NodeFilter ElementIdFilter = new HasAttributeFilter("id", elementId);
		OrFilter orFilter = new OrFilter(TitleFilter, ElementIdFilter); // 做一个逻辑OR
																		// Filter组合
		NodeList list = parser.extractAllNodesThatMatch(orFilter);

		StringBuffer text = new StringBuffer();
		for (int i = 0; i < list.size(); i++)
			text = text.append(list.elementAt(i).toHtml() + "\r\n");
		return text.toString().trim();
	}

	private void margerSearchCompany(String resultListStr) {
		cstocks = new ArrayList<CompanyStock>();
		Parser parser = null;
		NodeList LinkTagList = null;
		NodeFilter LinkTagFilter = null;
		try {
			parser = new Parser(resultListStr);
			LinkTagFilter = new NodeClassFilter(LinkTag.class);
			LinkTagList = parser.extractAllNodesThatMatch(LinkTagFilter);
			String to = "ss";
			for (int i = 0; i < LinkTagList.size(); i++) {
				CompanyStock stock = new CompanyStock();
				LinkTag link = (LinkTag) LinkTagList.elementAt(i);
				String linkStr = link.getLink();
				stock.setLinkAddr(linkStr);
				String linkText = link.getLinkText();
				String[] str = linkText.split("\\(");
				if (str.length <= 1) {
					continue;
				}
				to = linkStr.indexOf("sz") > -1 ? "sz" : "ss";
				stock.setStockName(str[0]);
				stock.setStockCode((str.length > 1 && str[1].length() > 0) ? str[1]
						.substring(0, str[1].length() - 1) : "");
				stock.setTeamOrg(to);
				cstocks.add(stock);
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}

	}
}
