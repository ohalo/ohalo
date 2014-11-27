package com.teddy.excel.util;

public class ProxySetting {
	private boolean isProxyOn;
	private String proxyHost;
	private int proxyPort;

	public boolean isProxyOn() {
		return isProxyOn;
	}

	public void setProxyOn(boolean isProxyOn) {
		this.isProxyOn = isProxyOn;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
}
