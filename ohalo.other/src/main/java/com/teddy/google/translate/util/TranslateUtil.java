/**
 * Copyright (c) blackbear, Inc All Rights Reserved.
 */
package com.teddy.google.translate.util;

import java.io.InputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.teddy.excel.util.ProxySetting;

/**
 * TranslateUtil
 * 
 * <pre>翻译工具
 * PS: 通过google translate
 * </pre>
 * 
 * @author catty
 * @version 1.0, Created on 2011/9/2
 */
public class TranslateUtil {

	protected static final String URL_TEMPLATE = "http://translate.google.com/?langpair={0}&text={1}";	
	protected static final String ID_RESULTBOX = "result_box";		
	protected static final String ENCODING = "UTF-8";

	public static final String AUTO = "auto"; // 自动判断来源语系
	public static final String TAIWAN = "zh-TW"; // 繁中
	public static final String CHINA = "zh-CN"; // �?��
	public static final String ENGLISH = "en"; // 英文
	public static final String JAPAN = "ja"; // 日文

	/**
	 * <pre>Google翻译
	 * PS: 交由google自动判断来源语系
	 * </pre>
	 * 
	 * @param text
	 * @param target_lang 目标语系
	 * @return
	 * @throws Exception
	 */
	public static String translate(final String text, final String target_lang) throws Exception {
		return translate(text, AUTO, target_lang);
	}
	
	/**
	 * <pre>Google翻译</pre>
	 * 
	 * @param text
	 * @param src_lang 来源语系
	 * @param target_lang 目标语系
	 * @return
	 * @throws Exception
	 */
	public static String translate(final String text, final String src_lang, final String target_lang, final ProxySetting setting)
			throws Exception {
		InputStream is = null;
		Document doc = null;
		Element ele = null;
		try {
			// create URL string
			String url = MessageFormat.format(URL_TEMPLATE,
					URLEncoder.encode(src_lang + "|" + target_lang, ENCODING),
					URLEncoder.encode(text, ENCODING));		
			
			System.out.println("[URL:] " + url);

			// connect & download html
			is = HttpClientUtil.downloadAsStream(url, setting);

			// parse html by Jsoup
			doc = Jsoup.parse(is, ENCODING, "");
			ele = doc.getElementById(ID_RESULTBOX);
			String result = ele.text();
			return result;

		} finally {
			IOUtils.closeQuietly(is);
			is = null;
			doc = null;
			ele = null;
		}
	}
	
	
	/**
	 * <pre>Google翻译</pre>
	 * 
	 * @param text
	 * @param src_lang 来源语系
	 * @param target_lang 目标语系
	 * @return
	 * @throws Exception
	 */
	public static String translate(final String text, final String src_lang, final String target_lang)
			throws Exception {
		InputStream is = null;
		Document doc = null;
		Element ele = null;
		try {
			// create URL string
			String url = MessageFormat.format(URL_TEMPLATE,
					URLEncoder.encode(src_lang + "|" + target_lang, ENCODING),
					URLEncoder.encode(text, ENCODING));		
			
			System.out.println("[URL:] " + url);

			// connect & download html
			is = HttpClientUtil.downloadAsStream(url);

			// parse html by Jsoup
			doc = Jsoup.parse(is, ENCODING, "");
			ele = doc.getElementById(ID_RESULTBOX);
			String result = ele.text();
			return result;

		} finally {
			IOUtils.closeQuietly(is);
			is = null;
			doc = null;
			ele = null;
		}
	}

	/**
	 * <pre>Google翻译: �?��-->繁中</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String cn2tw(final String text) throws Exception {
		return translate(text, CHINA, TAIWAN);
	}

	/**
	 * <pre>Google翻译: 繁中-->�?��</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String tw2cn(final String text) throws Exception {
		return translate(text, TAIWAN, CHINA);
	}

	/**
	 * <pre>Google翻译: 英文-->繁中</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String en2tw(final String text) throws Exception {
		return translate(text, ENGLISH, TAIWAN);
	}

	/**
	 * <pre>Google翻译: 繁中-->英文</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String tw2en(final String text) throws Exception {
		return translate(text, TAIWAN, ENGLISH);
	}

	/**
	 * <pre>Google翻译: 日文-->繁中</pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String jp2tw(final String text) throws Exception {
		return translate(text, JAPAN, TAIWAN);
	}

	/**
	 * <pre>Google翻译: 繁中-->�?/pre>
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String tw2jp(final String text) throws Exception {
		return translate(text, TAIWAN, JAPAN);
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		TranslateUtil util = new TranslateUtil();
		String text = "my name is teddy";
		System.out.println(util.translate(text, ENGLISH, TAIWAN));		
	}
}