package cn.ohalo.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SSOAuth extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9013485250169609373L;

	static private ConcurrentMap accounts;
	static private ConcurrentMap SSOIDs;
	String cookiename = "";
	String domainname;
	private String gotoURL = " "; // 身份验证成功派发到的目标地址

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		domainname = config.getInitParameter("domainname");
		cookiename = config.getInitParameter("cookiename");
		SSOIDs = new ConcurrentHashMap();
		accounts = new ConcurrentHashMap();
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		gotoURL = request.getParameter("goto");
		String result = "failed";
		if (action == null) {
			handlerFromLogin(request, response);
		} else if (action.equals("authcookie")) {
			String myCookie = request.getParameter("cookiename");
			if (myCookie != null)
				result = authCookie(myCookie);
			out.print(result);
			out.close();
		} else if (action.equals("authuser")) {
			result = authNameAndPasswd(request, response);
			out.print(result);
			out.close();
		} else if (action.equals("logout")) {
			String myCookie = request.getParameter("cookiename");
			logout(myCookie);
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void logout(String UID) {
		System.out.println("Logout for " + UID);
		SSOIDs.remove(UID);
	}

	// 验证用户的密码有效性
	protected String authNameAndPasswd(HttpServletRequest request,
			HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String pass = (String) accounts.get(username);
		if ((pass == null) || (!pass.equals(password)))
			return "failed";
		String newID = createUID();
		SSOIDs.put(newID, username);
		return newID;
	}

	// 静态函数，验证cookie的有效性
	static public String authCookie(String value) {
		String result = (String) SSOIDs.get(value);
		if (result == null) {
			result = "failed";
			System.out.println("Authentication failed!");
		} else {
			System.out.println("Authentication success!");
		}
		return result;
	}

	// 静态函数，验证用户名的有效性
	static public String authUserAndPass(String username, String password) {
		String pass = (String) accounts.get(username);
		if ((pass == null) || (!pass.equals(password)))
			return "failed";
		String newID = createUID();
		SSOIDs.put(newID, username);
		return username;
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}

	// </editor-fold>

	private void handlerFromLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String pass = (String) accounts.get(username);
		if ((pass == null) || (!pass.equals(password)))
			getServletContext().getRequestDispatcher("/failed.html").forward(
					request, response);
		else {
			gotoURL = request.getParameter("goto");
			String newID = createUID();
			SSOIDs.put(newID, username);
			Cookie wangyu = new Cookie(cookiename, newID);
			wangyu.setDomain(domainname);
			wangyu.setMaxAge(60000);
			wangyu.setValue(newID);
			wangyu.setPath("/");
			response.addCookie(wangyu);
			System.out.println("login success, goto back url:" + gotoURL);
			if (gotoURL != null) {
				PrintWriter out = response.getWriter();
				response.sendRedirect(gotoURL);
				out.close();
			}
		}
	}

	// 创建用户的身份标识
	static private String createUID() {
		Date now = new Date();
		long time = now.getTime();
		return "Auth" + time;
	}
}
