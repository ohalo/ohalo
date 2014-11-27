package org.ohalo.resource.util;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <pre>
 * 功能：AttachUtil 附件工具类
 * 作者：赵辉亮
 * 日期：2013-5-16上午10:32:53
 * </pre>
 */
public class AttachUtil {

	/* 系统文件可以上传的后缀集合* */
	private static Set<String> stuffixSet = new HashSet<String>();
	/* 系统文件不可以上传的后缀集合 */
	private static Set<String> noStuffixSet = new HashSet<String>();
	// 日志打印
	private static Log logger = LogFactory.getLog(AttachUtil.class);

	/**
	 * <pre>
	 * 初始化系统文件后缀
	 * 包括图片
	 * 压缩，
	 * 2003 office文档
	 * 2007 office文档
	 * sql语句
	 * 文本
	 * pdf
	 * </pre>
	 */
	static {
		// 图片类型
		stuffixSet.add("jpg");
		stuffixSet.add("png");
		stuffixSet.add("gif");
		stuffixSet.add("bmp");
		// 超文本标记类型
		stuffixSet.add("html");
		stuffixSet.add("htm");
		// 压缩类型
		stuffixSet.add("zip");
		stuffixSet.add("rar");
		// office 文件类型
		stuffixSet.add("xls");
		stuffixSet.add("xlsx");
		stuffixSet.add("doc");
		stuffixSet.add("docx");
		stuffixSet.add("pptx");
		stuffixSet.add("ppt");
		// pdf类型文件
		stuffixSet.add("pdf");
		// sql文件
		stuffixSet.add("sql");
		// txt文件
		stuffixSet.add("txt");

		// 不可上传文件类型 可执行文件
		// exe window可执行文件
		noStuffixSet.add("exe");
		// 可执行文件 ext
		noStuffixSet.add("ext");
		// 可执行文件 bat
		noStuffixSet.add("bat");
		// 可执行文件 shell
		noStuffixSet.add("shell");
		// //可执行文件 bin
		noStuffixSet.add("bin");
	}

	/**
	 * 
	 * @param attachInputStream
	 * @param fileName
	 * @return
	 */
	public static String uploadFile(InputStream attachInputStream,
			String fileName) {

		String filetype = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 后缀+.
		filetype = "." + filetype;

		// 文件上传根目录
		String rootPath = AttachmentRootPath.getAttachRootPath();
		// 获取当前上传的年月日
		Calendar cal = Calendar.getInstance();
		// 当前日
		int day = cal.get(Calendar.DATE);
		// 当前月
		int month = cal.get(Calendar.MONTH) + 1;
		// 当前年
		int year = cal.get(Calendar.YEAR);
		// 拼接上传路径 以当前目录下attacment为 上传路径，然后在以年月日分割为每个子目录，最后加入转换文件名
		String xdfilePath = "/attachment/" + year + "/" + month + "/" + day
				+ "/" + PreparatorUtil.getUUIDRandom() + filetype;
		// 获取全路径
		String newAttachPath = rootPath + xdfilePath;

		try {
			// copy文件，到上面拼接的路径下面
			FileUtils.copyInputStreamToFile(attachInputStream, new File(
					newAttachPath));
			// 捕获异常
		} catch (Exception e) {
			// 返回异常信息
			return "\"msg\":\"文件上传失败，请重新上传!\"";
		}
		// 返回上传的路径
		return xdfilePath;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明： 上传文件，如果文件大小超过 <code>maxUploadFileSize</code> 记录参数，则返回错误信息
	 * 
	 * 第一。单个文件最大不能超过5m
	 * 第二。相互关联的文件最大不能超过10m
	 * 第三。不可以上传可执行文件（exe ,bin ,bat ,shell ）
	 * 作者：赵辉亮
	 * 日期：2013-5-16. 上午10:34:42
	 * </pre>
	 * 
	 * @param attach
	 *            附件
	 * @param maxUploadFileSize
	 *            最大文件上传size
	 * @return
	 */
	public static String uploadFile(File attach, String fileName,
			List<String> fileSizes, long maxUploadFileSize) {

		// 判断总文件是否大于10m
		if (!isValid(fileSizes)) {
			return "\"msg\":\"总文件大小超过10M，请重新上传\"";
		}
		// 获取文件后缀
		String filetype = fileName.substring(fileName.lastIndexOf(".") + 1);

		// if (!stuffixSet.contains(filetype)) {
		// return "\"msg\":\"文件类型无效!文件无法上传\"";
		// }
		// 如果所属后缀为不可上传后缀，则返回
		if (noStuffixSet.contains(filetype)) {
			return "\"msg\":\"文件类型无效,文件无法上传!\"";
		}
		// 后缀+.
		filetype = "." + filetype;

		// 文件上传根目录
		String rootPath = AttachmentRootPath.getAttachRootPath();
		// 获取当前上传的年月日
		Calendar cal = Calendar.getInstance();
		// 当前日
		int day = cal.get(Calendar.DATE);
		// 当前月
		int month = cal.get(Calendar.MONTH) + 1;
		// 当前年
		int year = cal.get(Calendar.YEAR);
		// 拼接上传路径 以当前目录下attacment为 上传路径，然后在以年月日分割为每个子目录，最后加入转换文件名
		String xdfilePath = "/attachment/" + year + "/" + month + "/" + day
				+ "/" + PreparatorUtil.getUUIDRandom() + filetype;
		// 获取全路径
		String newAttachPath = rootPath + xdfilePath;

		try {
			// copy文件，到上面拼接的路径下面
			FileUtils.copyFile(attach, new File(newAttachPath));
			// 捕获异常
		} catch (Exception e) {
			// 返回异常信息
			return "\"msg\":\"文件上传失败，请重新上传!\"";
		}
		// 返回上传的路径
		return xdfilePath;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：对字符进行转码操作
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {
		// 设置stringbuffer类
		StringBuffer sb = new StringBuffer();
		// 进行对字符串的循环操作
		for (int i = 0; i < s.length(); i++) {
			// 转换成char类型字符
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					logger.error("将文件名中的汉字转为UTF8编码的串时错误，输入的字符串为：" + s);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：已上传文件和当前上传文件总大小
	 *  根据总的文件大小，判断该文件是否超过10m
	 * 作者：刘崇丛
	 * 日期： 2013-4-27 上午10:38:01
	 * @param sizeList
	 * @return
	 * </pre>
	 */
	private static boolean isValid(List<String> fileSizes) {
		String size = null;
		double count = 0;
		boolean flag = true;
		for (String str : fileSizes) {
			// 计算改文件如果是mb ，则直接转化成数字
			if (str.contains("MB")) {
				size = str.substring(0, str.length() - 3);
				count += Integer.valueOf(size);
				// 计算该文件如果是kb，则/1024返回以mb形式的数字
			} else if (str.contains("KB")) {
				size = str.substring(0, str.length() - 3);
				count += Double.valueOf(size) / 1024;
				// 计算该文件大小如果为bytes则/(1024*1024)返回以mb形式的数字
			} else if (str.contains("bytes")) {
				size = str.substring(0, str.length() - 6);
				count += Double.valueOf(size) / (1024 * 1024);
			}
		}
		// 最后把所有的文件的大小综合，以mb形式输出的大小于10判断，如果>10则返回false
		if (count > 10) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @Description 从硬盘上删除文件
	 * @param filePath
	 * @return
	 * @author 孙升
	 * @date 2012-12-28 上午9:54:05
	 * @version V1.0
	 */
	public static boolean deleteFile(String filePath) {
		// 获取当前文件路径信息
		String realPath = AttachmentRootPath.getAttachRootPath() + "/"
				+ filePath;
		// 找到该文件的实体
		File file = new File(realPath);
		// 判断该文件的有效性
		if (file.isFile() && file.exists()) {
			// 删除该文件
			file.delete();
			return true;
		} else {
			return false;
		}
	}
}