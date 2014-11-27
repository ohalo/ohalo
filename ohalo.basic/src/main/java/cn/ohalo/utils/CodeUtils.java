package cn.ohalo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.util.Base64Codec;

public class CodeUtils {
	private static Log logger = LogFactory.getLog(CodeUtils.class);
	private static MessageDigest MD5 = null;

	static {
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			logger.debug(ex);
		}
	}

	public static String encode(String value) {
		String result = "";
		if (value == null) {
			return result;
		}
		Base64Codec baseEncoder = new Base64Codec();
		try {
			result = baseEncoder.encode(MD5.digest(value.getBytes("utf-8")));
		} catch (Exception ex) {
		}
		return result;
	}
}