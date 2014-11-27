package cn.ohalo.el.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ohalo.el.db.RestaurantDB;
import cn.ohalo.el.entity.Restaurant;
import cn.ohalo.utils.Guid;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class RestaurantServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5275682810068258294L;

	private static Log logger = LogFactory.getLog(RestaurantServlet.class);

	RestaurantDB resDb;

	private void initResDb() {
		resDb = RestaurantDB.getInstance();
		resDb.setCollectionName("restaurant");
	}

	private void insertRes(String name, String desc, String address,
			Long phoneNum, String id) {
		Restaurant rs = new Restaurant();
		rs.setAddress(address);
		rs.setCreateDate(new Date());
		rs.setDesc(desc);
		rs.setId(id);
		rs.setName(name);
		rs.setPhoneNum(phoneNum);
		resDb.insert(rs);
	}

	private void updateRes(String name, String desc, String address,
			Long phoneNum, String id) {
		Restaurant rs = new Restaurant();
		rs.setAddress(address);
		rs.setCreateDate(new Date());
		rs.setDesc(desc);
		rs.setId(id);
		rs.setName(name);
		rs.setPhoneNum(phoneNum);
		DBObject queryparams = new BasicDBObject("id", id);
		resDb.update(queryparams, rs);
	}

	private void deleteRes(String id) {
		DBObject queryparams = new BasicDBObject("id", id);
		resDb.remove(queryparams);
	}

	private List<Restaurant> queryAll(String name, String address,
			Long phoneNum, String id, int skip, int limit) {
		DBObject queryparams = new BasicDBObject();
		if (StringUtils.isNotBlank(id)) {
			queryparams.put("id", id);
		}
		if (StringUtils.isNotBlank(name)) {
			queryparams.put("name", name);
		}

		if (StringUtils.isNotBlank(address)) {
			queryparams.put("address", address);
		}

		if (phoneNum > 0) {
			queryparams.put("phoneNum", phoneNum);
		}

		List<Restaurant> res = resDb.findAllAndSortAndLimit(queryparams, null,
				skip, limit);
		return res;
	}

	private void oprRes(HttpServletRequest req, HttpServletResponse resp) {
		initResDb();
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("jsp 编码转换失败，" + e1);
		}
		// insert 增加，delete 删除，update 修改
		String oprType = req.getParameter("oprType");
		String name = req.getParameter("name");
		String desc = req.getParameter("desc");
		String address = req.getParameter("address");
		String phoneNumStr = req.getParameter("phoneNum");
		Long phoneNum = Long.parseLong(StringUtils.isNumeric(phoneNumStr)
				&& phoneNumStr != "" ? phoneNumStr : "0");
		String id = req.getParameter("id");

		if (logger.isDebugEnabled()) {
			logger.debug("进入oprRes方法，调用参数：name=" + name + ",desc=" + desc
					+ ",address=" + address + ",phoneNum=" + phoneNum + ",id="
					+ id + ",oprType=" + oprType);
		}

		if (StringUtils.equals(oprType, "insert")) {
			insertRes(name, desc, address, phoneNum, Guid.newDCGuid("res"));
		}

		if (StringUtils.equals(oprType, "update")) {
			updateRes(name, desc, address, phoneNum, id);
		}

		if (StringUtils.equals(oprType, "delete")) {
			deleteRes(id);
		}

		if (StringUtils.equals(oprType, "query")) {
			String skipStr = req.getParameter("skip");
			String limitStr = req.getParameter("limit");
			int skip = Integer.parseInt(StringUtils.isNumeric(skipStr)
					&& skipStr != "" ? skipStr : "0");
			int limit = Integer.parseInt(StringUtils.isNumeric(limitStr)
					&& limitStr != "" ? limitStr : "10");
			List<Restaurant> res = queryAll(name, address, phoneNum, id, skip,
					limit);
			req.setAttribute("res", res);
			req.setAttribute("skip", skip);
			req.setAttribute("limit", limit);
		}

		RequestDispatcher dispatcher = req
				.getRequestDispatcher("jsp/home/res.jsp");
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			logger.error("跳转失败!", e);
		} catch (IOException e) {
			logger.error("跳转失败!", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		oprRes(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		oprRes(req, resp);
	}
}