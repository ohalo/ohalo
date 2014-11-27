package org.ohalo.pomelo.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.ohalo.base.action.BaseController;
import org.ohalo.base.utils.LogUtils;
import org.ohalo.pomelo.result.Result;
import org.ohalo.pomelo.result.UserResult;
import org.ohalo.pomelo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author z.halo
 * @since 2013年10月12日 1.0
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Resource(name = "pomeloUserService")
	private UserService userService;

	/**
	 * 用户注册
	 * 
	 * @param nickName
	 * @param userName
	 * @param pwd
	 * @param type
	 *            两种类型:1.我是柚子，2.家有柚子
	 * @return
	 */
	@RequestMapping(value = "/userRegister")
	public @ResponseBody
	Result userRegister(
			@RequestParam(value = "nickName", required = false) String nickName,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "pwd") String pwd,
			@RequestParam(value = "type") String type) {

		LogUtils.debugParams("UserAction", "userRegister", "用户注册:[nickName:"
				+ nickName + ",userName:" + userName + ",pwd:" + pwd + "]");

		String email = "";
		String phoneNum = "";

		if (userName.indexOf("@") > -1) {
			email = userName;
		} else {
			String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(userName);
			String val = null;
			while (m.find()) {
				val = m.group();
			}
			if (val != null) {
				phoneNum = userName;
			}
		}
		try {
			userService.saveUserInfo(nickName, userName, pwd, phoneNum, email,
					type);
		} catch (Exception e) {
			LogUtils.errorMsg("UserAction", "userRegister", "用户注册:[nickName:"
					+ nickName + ",userName:" + userName + ",pwd:" + pwd + "]",
					e);
			return new Result(Result.BUSINESS_FAILURE_CODE, "注册用户失败!");
		}
		return new Result(Result.SUCCESS_CODE, "注册用户成功!");
	};

	/**
	 * 用户登录
	 * 
	 * @param type
	 *            用户类型
	 * @param userName
	 *            用户名称
	 * @param pwd
	 *            用户密码
	 * @return
	 */
	@RequestMapping(value = "/userLogin")
	public @ResponseBody
	UserResult userLogin(@RequestParam(value = "type") String type,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "pwd") String pwd) {
		LogUtils.debugParams("UserAction", "userLogin", "用户登录:[userName:"
				+ userName + ",pwd:" + pwd + ",type:" + type + "]");

		UserResult result = userService.queryUserInfo(userName, pwd, type);
		if (result != null) {
			result.setResultCode(Result.SUCCESS_CODE);
			result.setResultMsg("用户登录成功!");
		} else {
			result = new UserResult();
			result.setResultCode(Result.BUSINESS_FAILURE_CODE);
			result.setResultMsg("用户登录失败!");
		}
		return result;
	};

	/**
	 * 
	 * 忘记密码，密码修改
	 * 
	 * @param email
	 * @param pPhone
	 * 
	 * @return
	 */
	@RequestMapping(value = "/forgetPwd")
	public @ResponseBody
	Result forgetPwd(@RequestParam(value = "email") String email, String pPhone) {
		return null;
	};

	/**
	 * 更新用户信息
	 * 
	 * @param uPassword
	 * @param pPhone
	 * @param pName
	 * @param pBirthdate
	 * @param pSex
	 * @return
	 */
	@RequestMapping(value = "/updateUserInfo")
	public @ResponseBody
	Result updateUserInfo(
			@RequestParam("uAccount") String uaccount,
			@RequestParam(value = "uPassword", required = false) String uPassword,
			@RequestParam(value = "pPhone", required = false) String pPhone,
			@RequestParam(value = "pName", required = false) String pName,
			@RequestParam(value = "pBirthdate", required = false) String pBirthdate,
			@RequestParam(value = "pSex", required = false) String pSex,
			@RequestParam(value = "email", required = false) String email) {
		try {
			userService.updateUserInfo(uaccount, uPassword, pPhone, pName,
					pBirthdate, pSex, email);
		} catch (Exception e) {
			LogUtils.errorMsg("UserAction", "updateUserInfo", "更新用户信息失败!", e);
			return new Result(Result.BUSINESS_FAILURE_CODE, "更新用户信息失败!");
		}
		return new Result(Result.SUCCESS_CODE, "更新用户信息成功!");
	}
}