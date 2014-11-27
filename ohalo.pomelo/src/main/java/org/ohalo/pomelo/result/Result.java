package org.ohalo.pomelo.result;

import java.io.Serializable;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5516843703835024148L;
	/**
	 * 
	 */
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

	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Result(int resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	/**
	 * @return the resultCode
	 */
	public int getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 *            the resultCode to set
	 */
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg
	 *            the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}
