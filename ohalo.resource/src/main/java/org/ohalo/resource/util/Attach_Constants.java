package org.ohalo.resource.util;

/**
 * 
 * @author z.halo
 * @since 2013年10月12日 1.0
 */
public class Attach_Constants {
	/** 日期时间格式 */
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** 保存附件数据表 */
	public static String ATTACH_TABLE_NAME = "T_ATTACH_RESOURCE";
	/** 附件log内容 */
	public static String ATTACH_LOG_CONTENT = "附件上传成功";
	/** 文件大小 单位K */
	public static long ATTACH_SIZE_K = 1024;
	/** 文件大小 单位M */
	public static long ATTACH_SIZE_M = 1048576;
	/** 文件大小 单位G */
	public static long ATTACH_SIZE_G = 1073741824;
	/** 文件大小超出500M上限标识 */
	public static String ATTACH_MAX_LIMIT = "MAX";
	/** 文件大小超出500M上限提示内容 */
	public static String ATTACH_MAX_LIMIT_MAG = "文件上传至服务器失败!超出最大上限500M";
	/**
	 * @Fields SERVER_URL : 用来指定服务器地址, 方便测试环境和正式环境的切换
	 */
	private static String SERVER_URL = "http://localhost:8881/";
	// 系统附件url地址
	public static String SYS_URL = "http://192.168.19.99:8881/attach/";
	// 系统附件下载地址
	public static String SYS_DOWNLOAD_URL = SERVER_URL
			+ "attach/attachment/download.action?filePath=";
	// 系统附件删除地址
	public static String SYS_DELETE_URL = SERVER_URL
			+ "attach/attachment/delete.action?attachGuid=";

}
