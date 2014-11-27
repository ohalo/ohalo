package org.ohalo.base.action;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.ohalo.base.data.Result;
import org.ohalo.base.entity.BaseEntity;
import org.ohalo.base.utils.JsonUtils;

/**
 * 
 * @author halo
 * @since 2013-8-18 上午10:23:46
 */
@SuppressWarnings("unchecked")
public class BaseController {

	public Result toResult(Object obj) {
		Result result = new Result();
		if (obj instanceof BaseEntity) {
			result.setEntity((BaseEntity) obj);
		} else if (obj instanceof Collection) {
			result.setEntitys((List<BaseEntity>) obj);
		} else if (obj instanceof Result) {
			result = (Result) obj;
		} else if (obj instanceof String) {
			result.setJsonStr(obj.toString());
		}
		return result;
	}

	protected void returnDBError(Object obj, HttpServletResponse response) {
		Result result = toResult(obj);
		result.setResultCode(Result.SYSTEM_ERROR_CODE);
		result.setResultMsg("数据操作异常");
		JsonUtils.writeJsonObject(response, result);
	}

	protected void returnAUTHError(Object obj, HttpServletResponse response) {

		Result result = toResult(obj);
		result.setResultCode(Result.AUTH_FAILURE_CODE);
		result.setResultMsg("用户认证失败");
		JsonUtils.writeJsonObject(response, result);
	}

	protected void returnInvalidParamsError(Object obj,
			HttpServletResponse response, String params) {
		Result result = toResult(obj);
		result.setResultCode(Result.INVALID_REQUEST_CODE);
		result.setResultMsg("无效的参数:" + params);
		JsonUtils.writeJsonObject(response, result);
	}

	protected void returnSuccess(Object obj, String msg,
			HttpServletResponse response) {
		Result result = toResult(obj);
		result.setResultCode(Result.SUCCESS_CODE);
		result.setResultMsg(msg);
		JsonUtils.writeJsonObject(response, result);
	}
}
