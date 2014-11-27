package org.ohalo.resource.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ohalo.base.utils.PropertiesUtils;

/**
 * 
 * 
 * <pre>
 * 功能：Propertiesconfiguration.java
 * 作者：赵辉亮
 * 日期：2013-6-25下午6:03:17
 * </pre>
 */
public class Propertiesconfiguration {
	private final static String APP_PROPERTIES_FILE = "com/deppon/fssc/module/attachment/server/META-INF/fileservers.properties";
	// private final static String APP_PROPERTIES_FILE =
	// "../META-INF/fileservers.properties";
	protected final static Log logger = LogFactory
			.getLog(Propertiesconfiguration.class);
	//
	protected static Properties p = null;
	//
	static {
		init();
	}

	/**
	 * init
	 */
	protected static void init() {
		try {
			p = PropertiesUtils.loadProperties(APP_PROPERTIES_FILE);
		} catch (IOException e) {
			logger.error("load " + APP_PROPERTIES_FILE
					+ " into Constants error!");
		}
	}

	/**
	 * 
	 * @param key
	 *            property key.
	 * @param defaultValue
	 */
	protected static String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

	/**
	 * 
	 * @param key
	 *            property key.
	 */
	public static String getStringProperty(String key) {
		return p.getProperty(key);
	}
}
