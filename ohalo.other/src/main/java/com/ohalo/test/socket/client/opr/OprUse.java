package com.ohalo.test.socket.client.opr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.alibaba.fastjson.JSONObject;
import com.ohalo.test.socket.client.domain.User;
import com.ohalo.test.socket.client.request.HttpRequester;
import com.ohalo.test.socket.client.response.HttpRespons;

/**
 * 
 * 
 * socket\\\\com.ohalo.test.socket.client.playcard\\\\\\OprUse.java
 * 
 * <pre>
 * ############################################
 *  
 *  操作用户信息
 *    返回已json形式的返回 如:{'username':'admin','password','admin'} 
 *  
 * ############################################
 * </pre>
 * 
 * @author Z.Halo
 * @version 1.0 ,2013-1-31
 */
public class OprUse {

	private String serverUrl;

	private HttpRequester requester;

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public User getUser() {
		Map<String, String> params = new HashMap<String, String>();
		User user = null;
		try {
			HttpRespons respons = requester.sendPost(serverUrl, params);
			Vector<String> returnResult = respons.getContentCollection();
			for (Iterator<String> iterator = returnResult.iterator(); iterator
					.hasNext();) {
				String result = iterator.next();
				if (result.startsWith("{") && result.endsWith("}")) {
					user = JSONObject.parseObject(result, User.class);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}
}
