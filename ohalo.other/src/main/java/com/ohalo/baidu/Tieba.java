package com.ohalo.baidu;

import java.io.IOException;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tieba {

	/**
	 * 
	 * @return ArrayList<String ��ַ����>
	 * @throws IOException
	 */
	public static HashMap<String, String> getHomePageHashMap(String homePage)
			throws IOException {
		if (homePage.contains("http://tieba.baidu.com/")) {
			Document doc = Jsoup.connect(homePage).get();
			Elements links = doc.select("a[href*=/p/]");
			HashMap<String, String> hs = new HashMap<String, String>();
			for (Element link : links) {
				if (link.attr("abs:title") != null
						&& (!link.attr("abs:title").equals(""))) {
					hs.put(link.attr("abs:href"),
							link.attr("abs:title")
									.replace("http://tieba.baidu.com/", "")
									.replace("/", "").replace("\\", ""));
				}
			}
			return hs;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param detailsPage
	 * @return
	 * @throws IOException
	 */
	public static Set<String> getDetailsPageImageList(String detailsPage)
			throws IOException {
		if (detailsPage.contains("http://tieba.baidu.com/p/")) {
			Set<String> set = new HashSet<String>();
			detailsPage = detailsPage + "?see_lz=1";
			Document doc = Jsoup.connect(detailsPage).get();
			Elements totalPage = doc.select("span[class=red]");
			int pageNumber = 0;
			for (Element src : totalPage) {
				try {
					if (pageNumber == 0) {
						pageNumber = Integer.parseInt(src.text());
					}
				} catch (Exception e) {
				}
			}
			for (int i = 1; i <= pageNumber; i++) {
				doc = Jsoup.connect(detailsPage + "&pn=" + i).get();
				Elements image = doc
						.select("img[src*=imgsrc.baidu.com/forum/]");
				for (Element src : image) {
					try {
						int width = Integer.parseInt(src.attr("width"));
						int height = Integer.parseInt(src.attr("height"));
						if (width >= 400 && height >= 400) {
							set.add(src.attr("abs:src"));
						}
					} catch (Exception e) {
					}
				}
			}
			return set;
		} else {
			return null;
		}
	}
}
