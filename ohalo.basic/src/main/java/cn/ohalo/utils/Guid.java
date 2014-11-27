/*
 * 
 */
package cn.ohalo.utils;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * @Title: Guid.java
 * @author 黄勇
 * @date 2012-10-31 下午2:18:19
 * @version V1.0
 */
public class Guid {

	/**
   * 
   */
	private static Random rd = new Random();

	/**
	 * 
	 * <pre>
	 * 方法体说明：实例化一个新的guid 
	 *  作为实体对象的主键
	 *  首先是以系统简体名称开始，FSSC
	 *  在加入当前时间
	 *  然后加入16位随机码
	 *  最后生成系统唯一键id
	 * 作者：赵辉亮
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param startStuffix
	 *            数字编码前缀
	 * 
	 * @return
	 */
	public synchronized static String newDCGuid(String startStuffix) {
		// 所属系统名称
		String sysCode = "OHALO";
		if (StringUtils.isNotBlank(startStuffix)) {
			sysCode = startStuffix;
		}
		// 定义id
		String id = "";
		// 记录当前时间 以long类型显示
		String time = System.currentTimeMillis() + "";
		// 如果id为空的话，则生成16位随机码
		if (id.equals("")) {
			id = Integer.toHexString(rd.nextInt(16)).toUpperCase()
					+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
					+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
					+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
					+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
					+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
					+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
					+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
					+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
					+ Integer.toHexString(rd.nextInt(16)).toUpperCase();
		}
		// 最后通过拼接随机码返回唯一键id
		String rtn = sysCode + time + id;
		return rtn;
	}

}
