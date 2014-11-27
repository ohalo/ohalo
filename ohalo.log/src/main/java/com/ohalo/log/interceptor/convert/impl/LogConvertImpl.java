package com.ohalo.log.interceptor.convert.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ohalo.log.interceptor.convert.LogConvert;

public class LogConvertImpl implements LogConvert {

	private static Log logger = LogFactory.getLog(LogConvertImpl.class);

	public void before(Method method, Object[] args) {
		logger.info("被执行类：" + method.getDeclaringClass() + ",执行方法[ "
				+ method.getName() + "] , 执行参数:" + Arrays.toString(args));
	}

	public void logInfo(Level level, Object message, Throwable e) {
		if (Level.INFO.equals(level)) {
			logger.info(message, e);
		} else if (Level.WARNING.equals(level)) {
			logger.error(message, e);
		} else {
			logger.debug(message, e);
		}
	}

	public void after(Object result) {
		logger.info("输出结果参数：" + result);
	}

}
