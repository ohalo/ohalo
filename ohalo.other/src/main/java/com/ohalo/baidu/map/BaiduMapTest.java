/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ohalo.baidu.map;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/***
 * @author <a href="mailto:halo26812@yeah.net">z.halo</a>
 * @version $Id: $
 */
@SuppressWarnings("unused")
public class BaiduMapTest {

	private static Log logger = LogFactory.getLog(BaiduMapTest.class);

	private String key = "0A24d6a28a955a60fe7cb589b408a31c";

	private String ipAddr = "http://api.map.baidu.com/geocoder/v2/";

	private String keyField = "ak";

	private String callBackField = "callback";

	private String outputField = "output";

	private String outputJson = "json";

	private String outputXml = "xml";

	private String addressField = "address";

	private String cityField = "city";

	private String callBack1 = "showLocation";

	private String callBack2 = "renderOption";

	/**
	 * 
	 * <pre>
	 * 方法体说明：(方法详细描述说明、方法参数的具体涵义)
	 * 作者：赵辉亮
	 * 日期：2013-7-22
	 * </pre>
	 */
	public void requestJsonAddress(String address, String city) {
		HttpClient client = new HttpClient();
		String url = ipAddr + "?" + keyField + "=" + key + "&" + callBackField
				+ "=" + callBack1 + "&" + outputField + "=" + outputJson + "&"
				+ addressField + "=" + address + "&" + cityField + "=" + city;
		// try {
		// client.startSession(new URL(url));
		// } catch (MalformedURLException e) {
		// logger.error("连接超时!", e);
		// }
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：查询公司基本信息情况
	 * 作者：赵辉亮
	 * 日期：2013-10-8
	 * </pre>
	 * 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void testSearchCompanyBaseInfo() throws IllegalArgumentException,
			IOException {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(
				"http://www.sgs.gov.cn/lz/etpsInfo.do?method=doSearch");
		method.setRequestBody(new NameValuePair[] {
				new NameValuePair("keyWords", new String("上海美泽文化传媒有限公司"
						.getBytes(), "ISO8859-1")),
				new NameValuePair("searchType", "1") });
		client.executeMethod(method);
		String response = new String(method.getResponseBody());
		System.out.println(response);
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：查询公司基本信息情况
	 * 作者：赵辉亮
	 * 日期：2013-10-8
	 * </pre>
	 * 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void testSearchCompanyDetailInfo() throws IllegalArgumentException,
			IOException {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(
				"http://www.sgs.gov.cn/lz/etpsInfo.do?method=viewDetail");
		method.setRequestBody(new NameValuePair[] { new NameValuePair("etpsId",
				"150000022004032200107") });
		client.executeMethod(method);
		String response = new String(method.getResponseBody());
		System.out.println(response);
	}

	public static void main(String[] args) throws HttpException, IOException {
		BaiduMapTest test = new BaiduMapTest();
		test.testSearchCompanyBaseInfo();
		// org.apache.http.client.HttpClient httpClient = new
		// DefaultHttpClient();
		// HttpPost post = new HttpPost(
		// "https://www.sgs.gov.cn/lz/etpsInfo.do?method=viewDetail");
		//
		// List<org.apache.http.NameValuePair> params = new
		// ArrayList<org.apache.http.NameValuePair>();
		// params.add(new BasicNameValuePair("etpsId",
		// "150000022004032200107"));
		// post.setEntity(new UrlEncodedFormEntity(params));
		//
		// HttpResponse httpResponse = httpClient.execute(post);
		// System.out.println(httpResponse.getEntity().getContent());
	}
}