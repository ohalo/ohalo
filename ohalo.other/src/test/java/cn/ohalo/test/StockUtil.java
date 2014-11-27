package cn.ohalo.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

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

public class StockUtil  {

	private static Log logger = LogFactory.getLog(StockUtil.class);


	public List<companyStock> returnJobList(URLConnection openConenction, String elementId) {
		List<companyStock> searchResult = null;
		try {
			Parser parser = new Parser(openConenction);
			parser.setEncoding("GBK");
			String resultListText = getText(elementId, parser);
			searchResult = margerSearchObject(resultListText);
		} catch (ParserException e) {
			logger.error("", e);
		}

		return searchResult;
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


	private List<companyStock> margerSearchObject(String resultListStr) {
		List<companyStock> stocks = new ArrayList<companyStock>();
		Parser parser = null;
		NodeList LinkTagList = null;
		NodeFilter LinkTagFilter = null;
		companyStock stock = new companyStock();
		try {
			parser = new Parser(resultListStr);
			LinkTagFilter = new NodeClassFilter(LinkTag.class);
			LinkTagList = parser.extractAllNodesThatMatch(LinkTagFilter);
			for (int i = 0; i < LinkTagList.size(); i++) {
				LinkTag link = (LinkTag) LinkTagList.elementAt(i);
				stock.setLinkAddr(link.getLink());
				String linkText = link.getLinkText();
				String[] str = linkText.split("\\(");
				stock.setStockName(str[0]);
				stock.setStockCode((str.length > 1 && str[1].length() > 0) ? str[1].substring(0,
						str[1].length() - 1) : "");
				stocks.add(stock);
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}

		return stocks;
	}
}

/**
 * 公司代码信息
 * 
 * @author halo
 * 
 */
class companyStock {

	private String stockCode;
	private String stockName;
	private String linkAddr;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	@Override
	public String toString() {
		return "companyStock [stockCode=" + stockCode + ", stockName="
				+ stockName + ", linkAddr=" + linkAddr + "]";
	}

}