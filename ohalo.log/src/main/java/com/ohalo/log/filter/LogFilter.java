/*
 * 
 */
package com.ohalo.log.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ohalo.log.config.ConfigParams;
import com.ohalo.log.context.LogRequestContext;
import com.ohalo.log.entity.RequestLogEntity;

/**
 * 
 * <pre>
 * 功能：LogFilter 日志过滤器 ,用于记录日志的信息
 * 作者：赵辉亮
 * 日期：2013-4-9上午9:51:35
 * </pre>
 */
public class LogFilter implements Filter {

	// 日志打印
	private static Log logger = LogFactory.getLog(LogFilter.class);

	/**
	 * 
	 * <pre>
	 * 方法体说明：初始化配置信息
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param filterConfig
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：doFilter 过滤
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param chain
	 * @throws IOException
	 *             输出IOException 信息
	 * @throws ServletException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		/** 取得HttpServletRequest,这里可以拿到HttpSession **/
		HttpServletRequest sreq = (HttpServletRequest) request;
		/** 寻找客户端是否采用Hessian协议,HTTP头部有此定义 **/
		String remoteReqMethod = sreq.getHeader("method-name");
		String remoteReqURL = sreq.getRequestURI();
		String contextPath = sreq.getContextPath();
		/** 去掉应用名称，具体部署的应用名称是可变的 **/
		if (contextPath != null && !"/".equals(contextPath)
				&& remoteReqURL.startsWith(contextPath)) {
			remoteReqURL = remoteReqURL.substring(contextPath.length());
		}
		// log 请求信息
		LogRequestContext logRequest = LogRequestContext.getCurrentContext();
		// 请求日志信息对象
		RequestLogEntity rli = new RequestLogEntity();
		// 分解请求URL，分解后的数据如：[, application, login, index.action]
		String[] strs = sreq.getRequestURI().split("/");
		// 获取当前hession信息
		HttpSession session = sreq.getSession();
		/**
		 * 通过session获取用户id
		 */
		String userId = (String) (session.getAttribute(ConfigParams.KEY_USER) != null ? session
				.getAttribute(ConfigParams.KEY_USER) : "null");

		// 设置当前Context 远程请求方法，远程请求地址，远程请求地址，用户id
		LogRequestContext.setCurrentContext(remoteReqMethod, remoteReqURL,
				request.getRemoteAddr(), userId);

		// if(logBuffer!=null){
		if (strs.length > 0) {
			// 请求id
			rli.setRequestID(logRequest.getRequestId());
			// 客户端ip 客户端端口
			rli.setClientIP(sreq.getHeader(ConfigParams.HEADER_XFORWARDEDFOR)
					+ "|" + sreq.getHeader(ConfigParams.HEADER_PROXYCLIENTIP)
					+ "|" + sreq.getHeader(ConfigParams.HEADER_WLPROXYCLIENTIP)
					+ "|" + sreq.getRemoteAddr());
			// 获取用户信息
			rli.setClientUserAgent(sreq.getHeader("user-agent"));
			// 获取服务端ip
			rli.setServerIP(sreq.getLocalAddr());
			// 服务端端口
			rli.setServerPort(sreq.getLocalPort());
			// 请求url地址
			rli.setRequestURI(sreq.getServletPath());
			// 用户id
			rli.setUser(userId);
			// 应用名称
			rli.setAppName(strs[1]);
			// 模块名称
			rli.setModuleName(strs[2 >= strs.length - 1 ? strs.length - 1 : 2]);
			// 请求action名称
			rli.setAction(strs[3 >= strs.length - 1 ? strs.length - 1 : 3]);
			// 遍历参数信息
			Enumeration<String> paramsName = sreq.getParameterNames();
			String params = "";
			while (paramsName.hasMoreElements()) {
				String name = paramsName.nextElement();
				String value = sreq.getParameter(name);
				if ("".equals(params)) {
					params = name + "=" + value;
				} else {
					params += "\n" + name + "=" + value;
				}
			}
			rli.setReqParameters(params);
		}
		// 设置开始时间
		Long startTime = System.currentTimeMillis();

		try {
			chain.doFilter(request, response);
			// 设置结束时间
			Long endTime = System.currentTimeMillis();
			// 设置请求开始时间
			rli.setReqStartTime(new Date(startTime));
			// 设置请求结束时间
			rli.setReqEndTime(new Date(endTime));
			// s设置请求时长
			rli.setReqDuration(endTime - startTime);
			// 记录日志信息
			logger.info(rli);
		} finally {
			LogRequestContext.remove();
		}
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：销毁
	 * 作者：赵辉亮
	 * 日期：2013-7-26
	 * </pre>
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
