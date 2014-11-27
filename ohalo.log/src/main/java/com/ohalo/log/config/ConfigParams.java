package com.ohalo.log.config;

/**
 * 
 * <pre>
 * 功能：ConfigParams 参数配置文件
 *  工具类的定位：
 *  1.作为一个软件开发者，缺乏想象力是最严重的罪过之一。
 *    我们经常把事情重复做一遍又一遍，但是我们很少改变这种方式。
 *    经过这些年开发，在我的工具箱里面有了一些每个项目我都需要用到的工具，
 *    烦人的重复工作不再是我的事.
 *  2.工具，汉语词语，原指工作时所需用的器具，
 *    后引申为为达到、完成或促进某一事物的手段。
 *    它的好处可以是机械性，也可以是智能性的。
 *    大部分工具都是简单机械。
 *    例如一根铁棍可以当作槓杆使用，力点离开支点越远，杠杆传递的力就越大.
 *    而工具类就是通过实现独立功能单元的一个工具
 *  3.直接使用的工具类，不但可以在本应用中使用这些工具类，也可以在其它的应用中使用，
 *    这些工具类中的大部分是可以在脱离各种框架和应用时使用的。
 *    工具类并在程序编写时适当使用和提取，将有助于提高开发效率、增强代码质量。
 *  4.对于软件开发过程中，需要使用到各种框架，而框架中往往都提供了相应的工具类。
 *    比如spring、struts、ibatis、hibernate。
 *    而java本身也提供不少的工具类供开发员进行特殊的处理
 * 作者：赵辉亮
 * 日期：2013-4-9上午10:02:39
 * </pre>
 */
public class ConfigParams {

	/**
	 * 简称<code>XFF</code>头，它代表客户端，也就是HTTP的请求端真实的IP
	 */
	public static final String HEADER_XFORWARDEDFOR = "X-Forwarded-For";

	/**
	 * 
	 */
	public static final String HEADER_PROXYCLIENTIP = "Proxy-Client-IP";

	/**
	 * 
	 */
	public static final String HEADER_WLPROXYCLIENTIP = "WL-Proxy-Client-IP";

	/**
	 * 用户id key定义
	 */
	public static final String KEY_USER = "FRAMEWORK__KEY_USER__";

	/**
	 * 国际化key 定义
	 */
	public static final String KEY_LOCALE = "FRAMEWORK__KEY_LOCALE__";

	/**
	 * 请求key定义
	 */
	public static final String KEY_REQUEST_URL = "FRAMEWORK_KEY_REQUEST_URL";
	// the client send request type |
	// text/html;text/json;application-data/stream,etc
	public static final String KEY_REQUEST_TYPE = "FRAMEWORK_KEY_REQUEST_TYPE";
	/**
	 * 角色缓存key
	 */
	public static final String KEY_ROLE_CACHE = "FRAMEWORK_KEY_ROLE_CACHE";

	/**
	 * 功能缓存key
	 */
	public static final String KEY_FUNCTION_CACHE = "FRAMEWORK_KEY_FUNCTION_CACHE";

	/**
	 * 用户缓存key
	 */
	public static final String KEY_USER_CACHE = "FRAMEWORK_KEY_USER_CACHE";

	/**
	 * 日志使用类型 LogBuffer
	 */
	public static final String LOG_USETYPE_LOGBUFFER = "LogBuffer";

	/**
	 * 日志使用类型 Log4j
	 */
	public static final String LOG_USETYPE_LOG4J = "Log4j";
}
