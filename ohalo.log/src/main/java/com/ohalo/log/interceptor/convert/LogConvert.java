package com.ohalo.log.interceptor.convert;

import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * 
 * <pre>
 * ###description###
 * 
 * 日志信息转换类，我們可以根據獲取到得方法信息和參數信息，來自定義需要進行儲存的數據信息
 * 
 * #################
 * </pre>
 * 
 * @author Z.D.Halo
 * @since 2013-1-13
 * @version 1.0
 */
public interface LogConvert {

	/**
	 * 在方法执行开始的时候执行，记录方法名和方法的参数
	 * 
	 * @author Z.D.Halo
	 * @param method
	 *            方法属性
	 * @param args
	 *            参数
	 */
	public void before(Method method, Object[] args);

	/**
	 * 
	 * 在方法执行的过程中，可能会出现一些异常或者是其他的信息，执行logInfo方法
	 * 
	 * @author Z.D.Halo
	 * @param level
	 *            日誌級別
	 * @param message
	 *            輸出信息
	 * @param e
	 *            異常信息
	 */
	public void logInfo(Level level, Object message, Throwable e);

	/**
	 * 在方法結束的時候執行，返回方法執行的結果參數信息
	 * 
	 * @author Z.D.Halo
	 * @param result
	 *            返回结果参数
	 */
	public void after(Object result);
}
