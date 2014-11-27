package com.deppon.crm.module.common.file.domain.exception;

/**
 * @description 文件exception
 * @author 安小虎
 * @version 0.1
 * @date 2012-5-5
 */

public class FileException extends RuntimeException {

	/**
	 * @fields serialVersionUID
	 */

	private static final long serialVersionUID = 8927081270074039087L;

	private String errCode;

	/**
	 * constructer
	 */
	public FileException(FileExceptionType type) {
		this.setErrCode(FileExceptionType.getValue(type));
	}

	/**
	 * @return the errCode 下午7:19:30
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * @param errCode
	 *            the errCode to set 下午7:19:30
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
}
