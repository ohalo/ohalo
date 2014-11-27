/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: PropertiesUtils.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package org.ohalo.base.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

/**
 * 
 * 
 * <pre>
 * 功能：PropertiesUtils.java Properties Util函数.
 * 作者：calvin
 * 日期：2013-6-25下午6:03:17
 * </pre>
 */
public class PropertiesUtils {

	/* 编码 */
	private static final String DEFAULT_ENCODING = "UTF-8";

	/* 打印日志 */
	private static Logger logger = LoggerFactory
			.getLogger(PropertiesUtils.class);
	/* properties 文件 */
	private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	/* 资源加载 */
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	/**
	 * 
	 * <pre>
	 * 方法体说明：载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的载入. 文件路径使用Spring Resource格式,
	 * 文件编码使用UTF-8.
	 * 作者：calvin
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param resourcesPaths
	 * @return
	 * @throws IOException
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 */
	public static Properties loadProperties(String... resourcesPaths)
			throws IOException {
		// 定义实体对象
		Properties props = new Properties();

		// 遍历循环
		for (String location : resourcesPaths) {
			// 打印日志信息
			logger.debug("Loading properties file from:" + location);
			// 流输出
			InputStream is = null;
			try {
				// 加载
				Resource resource = resourceLoader.getResource(location);
				// 返回流信息
				is = resource.getInputStream();
				// 配置文件加载
				propertiesPersister.load(props, new InputStreamReader(is,
						DEFAULT_ENCODING));
				// 捕获异常
			} catch (IOException ex) {
				// 打印日志
				logger.info("Could not load properties from classpath:"
						+ location + ": " + ex.getMessage());
				// 关闭流
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return props;
	}
}
