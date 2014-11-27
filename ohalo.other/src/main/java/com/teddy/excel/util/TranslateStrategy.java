package com.teddy.excel.util;

import com.teddy.google.translate.util.TranslateUtil;

public class TranslateStrategy implements IStrategy {
	
	private TranslateUtil translateUtil = new TranslateUtil();
	
	private String srcLang;
	
	private String targetLang;
	
	public TranslateStrategy(String srcLang, String targetLang){
		this.srcLang = srcLang;
		this.targetLang = targetLang;
	}

	@SuppressWarnings("static-access")
	@Override
	public String process(String content, ProxySetting setting) throws Exception {		
		return translateUtil.translate(content, srcLang, targetLang, setting);
	}

}
