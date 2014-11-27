package org.ohalo.base.data;

import java.util.List;

import org.ohalo.base.entity.BaseEntity;

/**
 * 返回结果操作信息
 * 
 * @author zhaohl
 * 
 */
public class Result implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5560393144038264266L;
	public static final int SUCCESS_CODE = 1;
	public static final int SYSTEM_ERROR_CODE = 0;
	public static final int AUTH_FAILURE_CODE = 2;
	public static final int BUSINESS_FAILURE_CODE = 3;
	public static final int INVALID_REQUEST_CODE = 4;

	/**
	 * 返回状态码
	 */
	private int resultCode;

	/**
	 * 返回状态信息
	 */
	private String resultMsg;

	/**
	 * 
	 */
	private BaseEntity entity;

	/**
	 * 
	 */
	private List<BaseEntity> entitys;

	/**
	 * json 字符串信息
	 */
	private String jsonStr;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public BaseEntity getEntity() {
		return entity;
	}

	public void setEntity(BaseEntity entity) {
		this.entity = entity;
	}

	public List<BaseEntity> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<BaseEntity> entitys) {
		this.entitys = entitys;
	}

	/**
	 * <pre>
	 * 方法体说明：(方法详细描述说明、方法参数的具体涵义)
	 * 作者：赵辉亮
	 * 日期：2013-8-20
	 * </pre>
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return "{resultCode:" + resultCode + ", resultMsg:" + resultMsg
				+ ", entity:" + entity + ", entitys:" + entitys + ", jsonStr:"
				+ jsonStr + "}";
	}

	/***
	 * @return the jsonStr
	 */
	public String getJsonStr() {
		return jsonStr;
	}

	/***
	 * @param jsonStr
	 *            the jsonStr to set
	 */
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
}
