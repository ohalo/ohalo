package cn.ohalo.company.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ohalo.company.entity.CompanyEntity;
import cn.ohalo.company.service.CompanyService;
import cn.ohalo.utils.JsonUtils;

/**
 * 公司 action
 * 
 * @author halo
 * 
 */
public class CompanyAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7065097491852420956L;

	private static CompanyService companyService = CompanyService.getInstance();

	private static Log logger = LogFactory.getLog(CompanyAction.class);

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		super.service(req, res);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("hello world!Get");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		queryAll(req, resp);
	}

	private void queryAll(HttpServletRequest req, HttpServletResponse resp) {
		if (logger.isDebugEnabled()) {

		}
		List<CompanyEntity> companys = companyService.queryAll(null, 1, 30);
		boolean flag = true;
		if (companys == null || companys.isEmpty()) {
			flag = false;
		}
		JsonUtils.writeJsonResult(resp, companys, flag, "查询公司信息"
				+ (flag == true ? "成功" : "为空"));
	}

	@SuppressWarnings("unused")
	private void insert(HttpServletRequest req, HttpServletResponse resp) {
		if (logger.isDebugEnabled()) {

		}
	}
}
