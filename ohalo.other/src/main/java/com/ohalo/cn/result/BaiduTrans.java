package com.ohalo.cn.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 13-9-28 Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
public class BaiduTrans implements Serializable {
	/***
   * 
   */
	private static final long serialVersionUID = -5636399152602758267L;
	private String from;
	private String to;
	private List<TransResult> trans_result;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<TransResult> getTrans_result() {
		return trans_result;
	}

	public void setTrans_result(List<TransResult> trans_result) {
		this.trans_result = trans_result;
	}
}
