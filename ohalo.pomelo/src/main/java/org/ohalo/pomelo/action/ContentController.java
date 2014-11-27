package org.ohalo.pomelo.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ohalo.base.action.BaseController;
import org.ohalo.base.utils.LogUtils;
import org.ohalo.pomelo.result.ContentResult;
import org.ohalo.pomelo.result.Person;
import org.ohalo.pomelo.result.Result;
import org.ohalo.pomelo.result.UserResult;
import org.ohalo.pomelo.service.ContentService;
import org.ohalo.pomelo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author z.halo
 * @since 2013年10月16日 1.0
 */
@RequestMapping("/content")
@Controller
public class ContentController extends BaseController {

	@Resource(name = "pomeloContentService")
	private ContentService contentService;

	@Resource(name = "pomeloUserService")
	private UserService userService;

	@RequestMapping(value = "/getIndexInfo")
	public @ResponseBody
	ContentResult queryIndexInfo(
			@RequestParam(value = "uaccount", required = false) String uAccount) {
		LogUtils.debugParams("ContentAction", "queryIndexInfo", "[uaccount:"
				+ uAccount + "]");
		ContentResult result = null;
		try {
			result = contentService.queryIndexInfo(uAccount);
			result.setResultCode(Result.SUCCESS_CODE);
			result.setResultMsg("数据库查询成功!");
		} catch (Exception e) {
			LogUtils.errorMsg("ContentAction", "queryIndexInfo", "[uaccount:"
					+ uAccount + "]", e);
			result = new ContentResult();
			result.setResultCode(Result.BUSINESS_FAILURE_CODE);
			result.setResultMsg("数据库查询失败!");
		}

		return result;
	}

	/**
	 * 删除个人状态信息
	 * 
	 * @param cid
	 * @return
	 */
	@RequestMapping(value = "/delContent")
	public @ResponseBody
	Result delContent(@RequestParam(value = "contentId") String cid) {
		LogUtils.debugParams("ContentAction", "delContent", "[cid:" + cid + "]");
		try {
			contentService.delContent(cid);
		} catch (Exception e) {
			LogUtils.errorMsg("ContentAction", "delContent", "[cid:" + cid
					+ "]", e);
			return new Result(Result.SYSTEM_ERROR_CODE, "删除数据失败!");
		}

		return new Result(Result.SUCCESS_CODE, "删除数据成功!");
	}

	/**
	 * 发表个人信息
	 * 
	 * @param uid
	 *            用户编码
	 * @param contentId
	 *            文章id
	 * @param content
	 *            文章内容
	 * @param ctype
	 *            文章类型 ： 照片，新闻，感人文章，说说
	 * @param issueDate
	 *            发布时间
	 * @return
	 */
	@RequestMapping(value = "/sendStatus")
	public @ResponseBody
	Result sendStatus(@RequestParam(value = "uaccount") String uaccount,
			@RequestParam(value = "contentId") String cId,
			@RequestParam(value = "ctitle") String ctitle,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "issueDate") String issueDate,
			@RequestParam(value = "type") String ctype) {
		LogUtils.debugParams("ContentAction", "sendStatus", "[uaccount:"
				+ uaccount + ", cId:" + cId + ", ctitle:" + ctitle
				+ ", content:" + content + ", ctype:" + ctype + "]");
		try {
			contentService.insertContent(uaccount, cId, ctitle, content, ctype,
					issueDate);
		} catch (Exception e) {
			LogUtils.errorMsg("ContentAction", "sendStatus", "[uaccount:"
					+ uaccount + ", cId:" + cId + ", ctitle:" + ctitle
					+ ", content:" + content + ", ctype:" + ctype + "]", e);
			return new Result(Result.BUSINESS_FAILURE_CODE, "插入内容信息失败!");
		}
		return new Result(Result.SUCCESS_CODE, "插入内容信息成功!");
	};

	/**
	 * 获取个人状态信息
	 * 
	 * @param ctype
	 *            1.展示活动，2.展示感人文章，3.家乡新闻,4.礼品,5.说说信息,6.照片
	 * @param uAccount
	 *            用戶賬戶
	 * @param start
	 *            开始位置
	 * @param limit
	 *            每页显示条数
	 * @return
	 */
	@RequestMapping(value = "/getStatus")
	public @ResponseBody
	ContentResult getStatus(@RequestParam(value = "type") String ctype,
			@RequestParam(value = "uaccount") String uAccount,
			@RequestParam(value = "start") Integer start,
			@RequestParam(value = "limit") Integer limit) {
		LogUtils.debugParams("ContentAction", "sendStatus", "[uaccount:"
				+ uAccount + ", ctype:" + ctype + ",start:" + start + ",limit:"
				+ limit + "]");
		ContentResult result = null;
		try {
			result = contentService.queryContentByUser(uAccount, ctype, start,
					limit);
		} catch (Exception e) {
			LogUtils.errorMsg("ContentAction", "sendStatus", "[uaccount:"
					+ uAccount + ", ctype:" + ctype + ",start:" + start
					+ ",limit:" + limit + "]", e);
			result = new ContentResult();
			result.setResultCode(Result.BUSINESS_FAILURE_CODE);
			result.setResultMsg("数据库查询用户信息失败!");
		}
		return result;
	};

	@RequestMapping(value = "/getUziStatus")
	public @ResponseBody
	ContentResult getQZoneStatus(
			@RequestParam(value = "uziType") String uziType,
			@RequestParam(value = "uaccount") String uAccount,
			@RequestParam(value = "start") Integer start,
			@RequestParam(value = "limit") Integer limit) {

		UserResult result = userService.queryUserInfo(uAccount, uziType);
		List<String> uaccounts = new ArrayList<String>();

		if (result == null) {
			return new ContentResult(Result.BUSINESS_FAILURE_CODE,
					"查询参数为空，请重新查询!");
		}

		uaccounts.add(uAccount);

		if (result.getRelPersons() != null && !result.getRelPersons().isEmpty()) {
			for (Person person : result.getRelPersons()) {
				if (person == null || person.getPerson() == null
						|| person.getPerson().getuName() == null) {
					continue;
				}
				uaccounts.add(person.getPerson().getuName());
			}
		}
		ContentResult cresult = contentService.queryContentByUnamesOrder(
				uaccounts, start, limit);
		cresult.setUid(uAccount);
		cresult.setResultCode(Result.SUCCESS_CODE);
		cresult.setResultMsg("查询文章列表成功!");
		return cresult;
	}

	/**
	 * 获取感人文章
	 * 
	 * @param ctype
	 * @param issueDate
	 * @return
	 */
	@RequestMapping(value = "/getTouchText")
	public @ResponseBody
	ContentResult getTouchText(@RequestParam(value = "type") String ctype,
			@RequestParam(value = "issueDate") String issueDate) {
		LogUtils.debugParams("ContentAction", "getTouchText", "[issueDate:"
				+ issueDate + ", ctype:" + ctype + "]");
		ContentResult result = null;
		try {
			result = contentService.queryContentByCtypeAdate(ctype, issueDate);
		} catch (Exception e) {
			LogUtils.errorMsg("ContentAction", "getTouchText", "[issueDate:"
					+ issueDate + ", ctype:" + ctype + "]", e);
			result = new ContentResult();
			result.setResultCode(Result.BUSINESS_FAILURE_CODE);
			result.setResultMsg("获取感人文章失败!");
		}
		return result;
	};
}