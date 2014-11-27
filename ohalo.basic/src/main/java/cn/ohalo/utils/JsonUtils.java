package cn.ohalo.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ohalo.result.data.Result;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("static-access")
public class JsonUtils {
	private static Log logger = LogFactory.getLog(JsonUtils.class);

	public static void writeJsonObject(HttpServletResponse response, Object obj) {
		PrintWriter pw = null;
		// jackson
		response.setCharacterEncoding("utf-8");
		try {
			pw = response.getWriter();
			pw.write(new JSONObject().toJSON(obj).toString());
		} catch (IOException e) {
			logger.error("json 转换失败!", e);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	public static void writeJsonResult(HttpServletResponse response,
			Object obj, boolean success, String msg) {
		Result result = new Result(success, msg, obj);
		writeJsonObject(response, result);
	}
}