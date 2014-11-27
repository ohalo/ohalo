package org.ohalo.base.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.ohalo.base.exception.BaseInfoException;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("static-access")
public class JsonUtils {

	public static void writeJsonObject(HttpServletResponse response, Object obj) {
		PrintWriter pw = null;
		// jackson
		response.setCharacterEncoding("utf-8");
		try {
			pw = response.getWriter();
			pw.write(new JSONObject().toJSON(obj).toString());
		} catch (IOException e) {
			LogUtils.errorMsg("JsonUtils", "writeJsonObject", "json 转换失败!", e);
			throw new BaseInfoException("json 转换失败!");
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}