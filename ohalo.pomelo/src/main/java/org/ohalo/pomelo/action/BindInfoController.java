package org.ohalo.pomelo.action;

import javax.annotation.Resource;

import org.ohalo.base.action.BaseController;
import org.ohalo.base.utils.LogUtils;
import org.ohalo.pomelo.result.RelPersonsResult;
import org.ohalo.pomelo.result.Result;
import org.ohalo.pomelo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 绑定用户信息
 * 
 * @author z.halo
 * @since 2013年10月14日 1.0
 */
@Controller
@RequestMapping("/bind")
public class BindInfoController extends BaseController {

	@Resource(name = "pomeloUserService")
	private UserService userService;

	/**
	 * 绑定信息(添加关系人员信息),添加父母接口
	 * 
	 * app应用 ， 关于我们， 意见反馈，版本更新
	 * 
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/addParents")
	public @ResponseBody
	Result addRelPerson(
			@RequestParam("uAccount") String uAccount,
			@RequestParam("pPhone") String pPhone,
			@RequestParam(value = "pName", required = false) String pName,
			@RequestParam(value = "pBirthdate", required = false) String pBirthdate,
			@RequestParam(value = "pAddress", required = false) String pAddress,
			@RequestParam(value = "prType", required = false) String prType) {
		LogUtils.debugParams("BindInfoAction", "queryRelPersons",
				"查询参数:[uAccount:" + uAccount + ",pPhone:" + pPhone + ",pName="
						+ pName + ",pBirthdate=" + pBirthdate + ",pAddress="
						+ pAddress + ",prType=" + prType + "]");
		try {
			userService.saveRelPeopleInfo(uAccount, pPhone, pName, pBirthdate,
					pAddress, prType);
		} catch (Exception e) {
			LogUtils.errorMsg("BindInfoAction", "queryRelPersons",
					"保存关联人信息出现异常,查询参数:[uAccount:" + uAccount + ",pPhone:"
							+ pPhone + ",pName=" + pName + ",pBirthdate="
							+ pBirthdate + ",pAddress=" + pAddress + ",prType="
							+ prType + "]", e);
			return new Result(Result.BUSINESS_FAILURE_CODE, "保存失败!");
		}
		return new Result(Result.SUCCESS_CODE, "保存成功!");
	};

	/**
	 * 获取用户关联的人员信息
	 * 
	 * 
	 * @param uAccount
	 *            用户账户名称
	 * @param uziType
	 *            柚子类型 1.我是柚子，2.我是父母
	 * @return
	 */
	@RequestMapping(value = "/queryRelPersons")
	public RelPersonsResult queryRelPersons(
			@RequestParam("uAccount") String uAccount,
			@RequestParam("uziType") String uziType) {
		LogUtils.debugParams("BindInfoAction", "queryRelPersons",
				"查询参数:[uAccount:" + uAccount + ",uziType:" + uziType + "]");
		RelPersonsResult result = null;
		try {
			result = userService.queryRelPersons(uAccount, uziType);
			result.setResultCode(Result.SUCCESS_CODE);
			result.setResultMsg("查询成功");
		} catch (Exception e) {
			LogUtils.errorMsg("BindInfoAction", "queryRelPersons",
					"查询关联关系人员失败!查询参数:[uAccount:" + uAccount + ",uziType:"
							+ uziType + "]", e);
			result.setResultCode(Result.SYSTEM_ERROR_CODE);
			result.setResultMsg("查询失败！");
		}
		return result;
	}

	/**
	 * 
	 * 根据用户的账户名称和关联电话，删除其关联信息
	 * 
	 * @param uAccount
	 * @param pPhone
	 * @return
	 */
	@RequestMapping(value = "/delRelParents")
	public @ResponseBody
	Result delRelPerson(@RequestParam(value = "uAccount") String uAccount,
			@RequestParam("pPhone") String pPhone) {
		LogUtils.debugParams("BindInfoAction", "delRelPerson",
				"查询参数:[uAccount:" + uAccount + ",pPhone:" + pPhone + "]");
		try {
			userService.deleteRelPerson(uAccount, pPhone);
		} catch (Exception e) {
			LogUtils.errorMsg("BindInfoAction", "delRelPerson",
					"查询参数:[uAccount:" + uAccount + ",pPhone:" + pPhone + "]", e);
			return new Result(Result.BUSINESS_FAILURE_CODE, "保存失败!");
		}
		return new Result(Result.SUCCESS_CODE, "删除成功!");
	}
}