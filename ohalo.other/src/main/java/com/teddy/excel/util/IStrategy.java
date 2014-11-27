package com.teddy.excel.util;

public interface IStrategy {
	public String process(String content, ProxySetting setting) throws Exception;
}
