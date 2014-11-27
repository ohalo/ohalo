package com.ohalo.test.readOnline;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 
 * socket\\\\com.ohalo.test.readOnline\\\\\\ReadFileInfo.java
 * 
 * <pre>
 * ############################################
 *  
 *   读取文件信息
 *  
 * ############################################
 * </pre>
 * 
 * @author Z.Halo
 * @version 1.0 ,2013-2-19
 */
public class ReadFileInfo {

	private static Log logger = LogFactory.getLog(ReadFileInfo.class);

	public static String readfilepath(String filepath) throws IOException {
		if (StringUtils.isBlank(filepath)) {
			return null;
		}
		return readFile(new File(filepath));
	}

	public static String readFile(File file) throws IOException {
		return FileUtils.readFileToString(file);
	}

	/**
	 * 拷贝源目录文件到目标目录文件，后缀名默认为.txt
	 * 
	 * @param srcpath
	 * @param destpath
	 *            d://translationDir/
	 * @throws IOException
	 */
	public static void copyFile(String srcpath, String destpath)
			throws IOException {
		File srcGFile = new File(srcpath);
		String name = srcpath.substring(srcpath.lastIndexOf("\\"),
				srcpath.length());
		File destGFile = new File(destpath + "\\" + name + ".jpg");
		logger.info("源目录path ： " + srcpath + ",目标目录地址: " + destGFile.getPath());
		FileUtils.copyFile(srcGFile, destGFile);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		try {

			/*
			 * String xy = PreparatorUtil.getfiletypeByFile(new File(
			 * "D:\\translationDir\\IT新员工手册（需修改）.ppt"));
			 * 
			 * System.out.println(xy);
			 */

			/*
			 * int i = 0;
			 * 
			 * File file = new File(
			 * "F:\\halo_cloudFile\\desketop\\halo_document\\员工借款单需求建议及解决方案.xlsx"
			 * ); File file1 = new
			 * File("D:\\translationDir\\员工借款单需求建议及解决方案.xlsx");
			 * 
			 * System.out.println(PreparatorUtil.getfiletypeByFile(file) + "&&"
			 * + PreparatorUtil.getfiletypeByFile(file1));
			 * 
			 * ReadFileInfo .copyFile(
			 * "F:\\halo_cloudFile\\desketop\\halo_document\\员工借款单需求建议及解决方案.xlsx"
			 * , "D:\\translationDir\\"); System.out.println(i);
			 */
			/**
			 * 10m 312ms 282ms 20m 2610ms 438ms
			 */

			Date startDate = new Date();
			long startTime = startDate.getTime();
			System.out.println("操作开始时间：" + startDate + ",即是秒数:" + startTime);

			File file = new File(
					"F:/halo_cloudFile/My Fetion file/165866326/test20.txt");
			String str = readFile(file);

			Date endDate = new Date();
			long endTime = endDate.getTime();
			System.out.println("操作结束时间：" + startDate + ",即是秒数:" + endTime
					+ ",中间差值：" + (endTime - startTime));

			// System.out.println(str);

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
