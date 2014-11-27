package cn.ohalo.parse.collect.impl;

import java.util.Date;

import org.jsoup.nodes.Element;

import cn.ohalo.parse.db.HtmlLinkDB;
import cn.ohalo.parse.entity.HtmlLink;

public class AdnbcwCollect extends HtmlLinkCollect {

	public AdnbcwCollect() {
		super();
	}

	@Override
	public HtmlLink ruleHtmlLink(Element element) {
		String hrefUrl = element.absUrl("href");
		String text = element.text();
		HtmlLink htmlLink = new HtmlLink();
		String domain = hrefUrl.substring("http://".length(),
				hrefUrl.indexOf("."));
		htmlLink.setDomain(domain);

		String[] ys = hrefUrl.split("/");

		if (!(hrefUrl.indexOf(".dnbcw.") > -1)) {
			htmlLink.setType(HtmlLink.STORE);
			htmlLink.setCode(ys[ys.length - 2]);
		} else if (hrefUrl.endsWith(".html") || hrefUrl.endsWith(".htm")) {
			htmlLink.setType(HtmlLink.ARTICLE);
			htmlLink.setCode(ys[ys.length - 2]);
		} else if (ys[ys.length - 1].indexOf(".") > -1) {
			htmlLink.setType(HtmlLink.OTHER);
			htmlLink.setCode(ys[ys.length - 2]);
		} else {
			htmlLink.setType(HtmlLink.ARTICLE_CATEGORY);
			htmlLink.setCode(ys[ys.length - 1]);
		}

		htmlLink.setLink(hrefUrl);
		htmlLink.setText(text);
		htmlLink.setCreateDate(new Date());
		return htmlLink;
	}

	public static void main(String[] args) {

		HtmlLinkDB db = HtmlLinkDB.getInstance();
		db.setCollectionName("cn.ohalo.htmlLink");

		AdnbcwCollect c = new AdnbcwCollect();
		c.execute("http://www.dnbcw.info/");

		System.out
				.println("=========================destory =======================");
	}

}
