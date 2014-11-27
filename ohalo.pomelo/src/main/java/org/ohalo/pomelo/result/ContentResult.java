package org.ohalo.pomelo.result;

import java.util.List;

import org.ohalo.pomelo.result.Result;

public class ContentResult extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9155158723875159454L;

	private String uid;

	private List<Content> contents;

	public ContentResult() {
		super();
	}

	public ContentResult(int resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
