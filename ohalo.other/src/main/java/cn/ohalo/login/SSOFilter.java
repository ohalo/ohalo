package cn.ohalo.login;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class SSOFilter implements Filter {

	private FilterConfig filterConfig = null;
	private String cookieName = "WangYuDesktopSSOID";
	private String SSOServiceURL = "http://wangyu.prc.sun.com:8080/SSOAuth/SSOAuth";
	private String SSOLoginPage = "http://wangyu.prc.sun.com:8080/SSOAuth/login.jsp";

	private static final boolean debug = true;

	@Override
	public void init(FilterConfig filterConfig) {

		this.setFilterConfig(filterConfig);
		if (filterConfig != null) {
			if (debug) {
				log("SSOFilter:Initializing filter");
			}
		}
		cookieName = filterConfig.getInitParameter("cookieName");
		SSOServiceURL = filterConfig.getInitParameter("SSOServiceURL");
		SSOLoginPage = filterConfig.getInitParameter("SSOLoginPage");
	}

	private void log(String msg) {
		filterConfig.getServletContext().log(msg);
	}

	/**
	 * Return a String representation of this object.
	 */
	public String toString() {
		if (filterConfig == null)
			return ("SSOFilter()");
		StringBuffer sb = new StringBuffer("SSOFilter(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		if (debug)
			log("SSOFilter:doFilter()");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String result = "failed";
		String url = request.getRequestURL().toString();
		String qstring = request.getQueryString();
		if (qstring == null)
			qstring = "";

		// 检查http请求的head是否有需要的cookie
		String cookieValue = "";
		javax.servlet.http.Cookie[] diskCookies = request.getCookies();
		if (diskCookies != null) {
			for (int i = 0; i < diskCookies.length; i++) {
				if (diskCookies[i].getName().equals(cookieName)) {
					cookieValue = diskCookies[i].getValue();

					// 如果找到了相应的cookie则效验其有效性
					result = SSOService(cookieValue);
					if (debug)
						log("found cookies!");
				}
			}
		}
		if (result.equals("failed")) { // 效验失败或没有找到cookie，则需要登录
			response.sendRedirect(SSOLoginPage + "?goto=" + url);
		} else if (qstring.indexOf("logout") > 1) {// logout服务
			if (debug)
				log("logout action!");
			logoutService(cookieValue);
			response.sendRedirect(SSOLoginPage + "?goto=" + url);
		} else {// 效验成功
			request.setAttribute("SSOUser", result);
			Throwable problem = null;
			try {
				chain.doFilter(req, res);
			} catch (Throwable t) {
				problem = t;
				t.printStackTrace();
			}
			if (problem != null) {
				if (problem instanceof ServletException)
					throw (ServletException) problem;
				if (problem instanceof IOException)
					throw (IOException) problem;
				sendProcessingError(problem, res);
			}
		}
	}

	private void sendProcessingError(Throwable t, ServletResponse response) {

		String stackTrace = getStackTrace(t);

		if (stackTrace != null && !stackTrace.equals("")) {

			try {

				response.setContentType("text/html");
				PrintStream ps = new PrintStream(response.getOutputStream());
				PrintWriter pw = new PrintWriter(ps);
				pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); // NOI18N

				// PENDING! Localize this for next official release
				pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
				pw.print(stackTrace);
				pw.print("</pre></body>\n</html>"); // NOI18N
				pw.close();
				ps.close();
				response.getOutputStream().close();
				;
			}

			catch (Exception ex) {
			}
		} else {
			try {
				PrintStream ps = new PrintStream(response.getOutputStream());
				t.printStackTrace(ps);
				ps.close();
				response.getOutputStream().close();
				;
			} catch (Exception ex) {
			}
		}
	}

	public static String getStackTrace(Throwable t) {

		String stackTrace = null;

		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (Exception ex) {
		}
		return stackTrace;
	}

	private String SSOService(String cookievalue) throws IOException {
		String authAction = "?action=authcookie&cookiename=";
		HttpClient httpclient = new HttpClient();
		GetMethod httpget = new GetMethod(SSOServiceURL + authAction
				+ cookievalue);
		try {
			httpclient.executeMethod(httpget);
			String result = httpget.getResponseBodyAsString();
			return result;
		} finally {
			httpget.releaseConnection();
		}
	}

	private void logoutService(String cookievalue) throws IOException {
		String authAction = "?action=logout&cookiename=";
		HttpClient httpclient = new HttpClient();
		GetMethod httpget = new GetMethod(SSOServiceURL + authAction
				+ cookievalue);
		try {
			httpclient.executeMethod(httpget);
			httpget.getResponseBodyAsString();
		} finally {
			httpget.releaseConnection();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
