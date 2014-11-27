package com.ohalo.cn.utils;

import com.alibaba.fastjson.JSON;
import com.ohalo.cn.result.BaiduTrans;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * @author Ericlin
 *         <p/>
 *         2013-9-25
 */
public class BaiduTranUtil {

	private static String url = "http://openapi.baidu.com/public/2.0/bmt/translate";

	private static String api_key = "rGtVzCmoYxyn1ybk7UqkQCE7";

	public static void main(String[] args) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.setQueryString(new NameValuePair[] {
				new NameValuePair("from", "zh"), new NameValuePair("to", "en"),
				new NameValuePair("client_id", api_key),
				// 多条内容用\n分隔
				new NameValuePair("q", "黑色。") });

		client.executeMethod(method);
		String response = new String(method.getResponseBodyAsString());
		System.out.println(response);
		method.releaseConnection();
		BaiduTrans buduTran = JSON.parseObject(response, BaiduTrans.class);
		System.out.print(buduTran.getFrom());
	}

	public static BaiduTrans toBaiduTran(String inputText, String fromTran,
			String toTran) throws IOException {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.setQueryString(new NameValuePair[] {
				new NameValuePair("from", fromTran),
				new NameValuePair("to", toTran),
				new NameValuePair("client_id", api_key),
				// 多条内容用\n分隔
				new NameValuePair("q", inputText) });

		client.executeMethod(method);
		String response = new String(method.getResponseBodyAsString());
		method.releaseConnection();
		BaiduTrans buduTran = JSON.parseObject(response, BaiduTrans.class);
		return buduTran;
	}
}