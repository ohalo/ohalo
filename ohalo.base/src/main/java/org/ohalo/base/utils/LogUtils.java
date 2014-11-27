package org.ohalo.base.utils;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <pre>
 * ###description###
 * 
 * 日志工具类，记录系统信息
 * 
 * #################
 * </pre>
 * 
 * @author Z.D.Halo
 * @since 2013-1-15
 * @version 1.0
 */
public class LogUtils {

	private static Log logger = LogFactory.getLog(LogUtils.class);

	public static void infoParams(String className, String methodName,
			Object... params) {
		logger.info("调用类：" + className + "的" + methodName + "这个方法，传递参数["
				+ Arrays.toString(params) + "]");
	}

	public static void debugParams(String className, String methodName,
			Object... params) {
		if (logger.isDebugEnabled()) {
			logger.debug("调用类：" + className + "的" + methodName + "这个方法，传递参数["
					+ Arrays.toString(params) + "]");
		}
	}

	public static void infoMsg(String className, String methodName, String msg) {
		logger.info("调用类：" + className + "的" + methodName + "这个方法，" + msg);
	}

	public static void errorMsg(String className, String methodName,
			String msg, Throwable t) {
		logger.error("调用类：" + className + "的" + methodName + "这个方法，" + msg, t);
	}
}
