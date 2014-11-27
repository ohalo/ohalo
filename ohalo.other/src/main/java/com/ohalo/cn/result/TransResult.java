package com.ohalo.cn.result;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 13-9-28 Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class TransResult implements Serializable {

	/***
   * 
   */
	private static final long serialVersionUID = -7227320997579186034L;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	private String src;
	private String dst;
}
