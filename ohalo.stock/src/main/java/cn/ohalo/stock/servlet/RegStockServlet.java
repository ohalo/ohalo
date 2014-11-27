package cn.ohalo.stock.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import cn.ohalo.stock.entity.RegStockInfo;
import cn.ohalo.stock.service.RegStockService;
import cn.ohalo.stock.socket.SinaStockClient;

public class RegStockServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2978961700966298057L;

	private String stockCode;

	private String teamOrg;

	private Integer buyNum;

	private Double buyPrice;

	private String oprType;

	private RegStockService rsService;

	private SinaStockClient client;

	/**
	 * 初始化参数
	 * 
	 * @param req
	 */
	private void initProperty(HttpServletRequest req) {
		rsService = RegStockService.getInstance();
		client = SinaStockClient.getInstance();

		stockCode = req.getParameter("stockCode");
		oprType = req.getParameter("oprType");
		teamOrg = req.getParameter("teamOrg");

		String bnum = req.getParameter("buyNum");
		String bprice = req.getParameter("buyPrice");

		if (StringUtils.isNotBlank(bnum) && StringUtils.isNumeric(bnum)) {
			buyNum = Integer.parseInt(bnum);
		}

		if (StringUtils.isNotBlank(bprice)) {
			buyPrice = Double.parseDouble(bprice);
		}
	};

	private String oprGetPrice(HttpServletRequest req) {
		Map<String, Double> params = client.getCurrentPrices();
		Double currentPrice = params.get(stockCode);
		req.setAttribute("currentPrice", currentPrice);
		return "success";
	}

	private String oprInsert() {
		RegStockInfo rinfo = new RegStockInfo(stockCode, teamOrg, buyNum,
				buyPrice);
		rsService.insert(rinfo);
		return "success";
	}

	private String oprUpdate() {
		RegStockInfo rinfo = new RegStockInfo(stockCode, teamOrg, buyNum,
				buyPrice);
		rsService.update(rinfo);
		return "success";
	}

	private String oprDelete() {
		RegStockInfo rinfo = new RegStockInfo(stockCode, teamOrg, buyNum,
				buyPrice);
		rsService.remove(rinfo);
		return "success";
	}

	private String oprQuery(HttpServletRequest req) {
		List<RegStockInfo> rs = rsService.queryAll();
		req.setAttribute("rsinfos", rs);
		return "success";
	}

	/**
	 * 操作控制，初始化参数，加上方法的调用分发
	 * 
	 * @param req
	 * @param resp
	 */
	public void oprDo(HttpServletRequest req, HttpServletResponse resp) {
		initProperty(req);
		String returnParams = null;
		if (StringUtils.equals(oprType, "insert")) {
			returnParams = oprInsert();
		} else

		if (StringUtils.equals(oprType, "update")) {
			returnParams = oprUpdate();
		} else

		if (StringUtils.equals(oprType, "delete")) {
			returnParams = oprDelete();
		} else

		if (StringUtils.equals(oprType, "query")) {
			returnParams = oprQuery(req);
		} else if (StringUtils.equals(oprType, "toGetPrice")) {
			returnParams = oprGetPrice(req);
		}

		if (returnParams.equals("success")) {
			RequestDispatcher dispatcher = req
					.getRequestDispatcher("jsp/stockIndex.jsp");
			try {
				dispatcher.forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		oprDo(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		oprDo(req, resp);
	}

}
