package com.deppon.crm.module.common.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.deppon.crm.module.common.file.domain.exception.FileException;
import com.deppon.crm.module.common.file.domain.exception.FileExceptionType;

/**
 * 
 * @description文件工具类
 * @author 安小虎
 * @version 0.1
 * @date 2012-5-5
 */
public class FileUtil {
	// private static Log log = LogFactory.getLog(FileUtil.class);

	/*
	 * 拷贝文件到指定目录
	 */
	private static long copyFile(File inFile, File outFile) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		if (outFile.exists() == false)
			outFile.createNewFile();

		FileInputStream in = new FileInputStream(inFile);
		FileOutputStream out = new FileOutputStream(outFile);
		byte[] buffer = new byte[length];
		while (true) {
			int ins = in.read(buffer);
			if (ins == -1) {
				in.close();
				out.flush();
				out.close();
				return new Date().getTime() - time;
			} else {
				out.write(buffer, 0, ins);
			}
		}
	}

	/*
	 * 创建文件名
	 */
	public static String createFileName(String fileName) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_");
		String name = formatter.format(new Date())
				+ UUID.randomUUID().toString();
		if (fileName.lastIndexOf(".") != -1) {
			// 得到后缀名
			String extension = fileName.substring(fileName.lastIndexOf("."));
			String allowedTypes = PropertiesUtil.getInstance()
					.getProperty("allowedTypes").trim();
			if (allowedTypes == null
					|| "".equals(allowedTypes)
					|| allowedTypes.indexOf(extension.substring(1,
							extension.length()).toLowerCase()) < 0) {
				FileException fe = new FileException(
						FileExceptionType.FILE_EXCEPTION_ALLOWEDTYPES);
				throw fe;
			}
			name = name + extension;
		}
		return name;
	}

	/*
	 * 保存文件到磁盘
	 */
	public static String saveFile2Disk(File inFile, String outPath,
			String fileName) throws Exception {
		if (inFile == null)
			return null;
		if (inFile.length() <= 0)
			return null;

		File file = new File(outPath);
		if (file.exists() == false) {
			file.mkdirs();
		}

		String filePath = outPath + "/" + createFileName(fileName);
		// log.info("---fileName=" + fileName + "；---path=" + filePath);
		File outFile = new File(filePath);
		copyFile(inFile, outFile);
		return filePath;
	}

	/*
	 * 删除磁盘文件
	 */
	public static boolean deleteDiskFile(String path) {
		File f = new File(path);
		if (!f.exists()) {
			FileException fe = new FileException(
					FileExceptionType.FILE_EXCEPTION_FILENOTEXISTS);
			throw fe;

		}
		return f.delete();
	}

	/*
	 * 根据配置文件中key获得模块
	 */
	public static InputStream getTemplateByKey(String key) {
		try {
			if (key != null && !"".equals(key)) {
				String templatePath = PropertiesUtil.getInstance().getProperty(
						"excelExportTemplatePath");
				String k_v = PropertiesUtil.getInstance().getProperty(key);
				if (k_v != null && !"".equals(k_v)) {
					String realPath = templatePath + "/"
							+ new String(k_v.getBytes(), "UTF-8");
					File file = new File(realPath);
					if (!file.exists()) {
						// log.info(realPath + "：对应文件不存在！");
						FileException fe = new FileException(
								FileExceptionType.FILE_EXCEPTION_ALLOWEDTYPES);
						throw fe;
					}
					return new FileInputStream(file);
				} else {
					// log.info("配置文件中没有对应模块的键值对！");
					FileException fe = new FileException(
							FileExceptionType.FILE_EXCEPTION_PROPKEYVALNOTEXISTS);
					throw fe;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 验证是否EXCEL文件
	 * */
	public static boolean validateFile(File inFile) {
		return true;
	}

	/*
	 * Java文件操作 获取文件扩展名
	 * 
	 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/*
	 * Java文件操作 获取不带扩展名的文件名
	 * 
	 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
}
