package cn.ohalo.stock.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import cn.ohalo.stock.entity.StockInfo;
import cn.ohalo.stock.service.StockInfoService;

public class StockServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2978961700966298057L;

	private StockInfoService stockInfoService;

	private String oprType;

	@Override
	public void init() throws ServletException {
		stockInfoService = StockInfoService.getInstance();
	}

	private void initParameter(HttpServletRequest req, HttpServletResponse resp) {
		oprType = req.getParameter("oprType");
	}

	private void queryBuyStock(HttpServletRequest req) {
		List<StockInfo> stockInfo = stockInfoService.queryAll();
		req.setAttribute("stockInfos", stockInfo);
	}

	private void oprMethod(HttpServletRequest req, HttpServletResponse resp) {
		initParameter(req, resp);
		if (StringUtils.equals(oprType, "query")) {
			queryBuyStock(req);
		}

		RequestDispatcher dispatcher = req
				.getRequestDispatcher("jsp/stockInfo.jsp");
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		oprMethod(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		oprMethod(req, resp);
	}

}
