package org.ohalo.app.action;

import org.ohalo.app.config.ParamsConfig;
import org.ohalo.app.entity.AppInfo;
import org.ohalo.app.result.AppResult;
import org.ohalo.app.service.AppService;
import org.ohalo.base.action.BaseController;
import org.ohalo.base.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/ohaloapp")
@Controller
public class OhaloAppAction extends BaseController {

	@Autowired
	private AppService appService;

	@RequestMapping("/qpomeloPhoneApp")
	public @ResponseBody
	AppResult queryNewVersionPomeloPhoneApp() {
		AppInfo info = appService.queryNewVersionApp(
				ParamsConfig.APP_CODE_POMELO, ParamsConfig.APP_TYPE_ANDRIOD_PHONE);
		AppResult result = new AppResult();
		if (info != null) {
			result.setAppInfo(info);
			result.setResultCode(Result.SUCCESS_CODE);
			result.setResultMsg("查询成功!");
		} else {
			result.setResultCode(Result.BUSINESS_FAILURE_CODE);
			result.setResultMsg("查询失败!");
		}
		return result;
	}

}
