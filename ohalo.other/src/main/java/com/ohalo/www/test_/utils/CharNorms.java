package com.ohalo.www.test_.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <pre>
 * 字符输入特征 数字[0-9] 
 * 小写字符[a-z] 
 * 大写字母[A-Z] 
 * 特殊字符~!@#$%^&*() 
 * 算法字符-=_+{}[]<>.
 * 文章字符\|;':",./?`
 * <pre>
 * @author Z.Halo
 * @version 1.0 ,2012-12-27
 */
public class CharNorms {

	private static final Log logger = LogFactory.getLog(CharNorms.class);
	
	public static void toGetCharRondom(String fixChar, int count) {
		if (count < 1) {
			return ;
		}

		if (count == 0) {// 0!=1
			return ;
		} else if (count == 1) {// 退出递归的条件
			return ;
		} else {
			for (int i = 0; i < 10; i++) {
				logger.info(fixChar + i + "(原始信息脚本):" + CryptoUtil.digestByMD5(fixChar + i));
				toGetCharRondom(fixChar + i, count - 1);
			}
			return ;
		}
	}

	public static void main(String[] args) {
		CharNorms.toGetCharRondom("0", 11);
	}
}
