package com.ohalo.test.socket.server;

import java.io.Serializable;

public class ResEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2894252654546784972L;

	private byte[] resByte;

	private int savefileLength;

	private int sourcefilelength;

	public byte[] getResByte() {
		return resByte;
	}

	public void setResByte(byte[] resByte) {
		this.resByte = resByte;
	}

	public int getSavefileLength() {
		return savefileLength;
	}

	public void setSavefileLength(int savefileLength) {
		this.savefileLength = savefileLength;
	}

	public int getSourcefilelength() {
		return sourcefilelength;
	}

	public void setSourcefilelength(int sourcefilelength) {
		this.sourcefilelength = sourcefilelength;
	}
}
