package com.ohalo.search.file.utils;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.crm.module.common.file.util.FileDlpUtil;

public class AutoUpdateFile {

	private static Log logger = LogFactory.getLog(AutoUpdateFile.class);

	public static void main(String[] args) {
		String path = "G:\\halo_file\\296557925\\FileRecv\\学习资料";

		HaloFileUtils.searchFilePath(new File(path), new String[] { "py",
				"zip", "ppt", "doc", "dotx", "docx", "rar", "xlsx", "pptx",
				"xls", "mpp", "JPG", "bmp", "ZIP", "7z", "gif","pdf" });

		Set<String> fileStore = HaloFileUtils.getFileStore();

		for (Iterator<String> iterator = fileStore.iterator(); iterator
				.hasNext();) {
			String dlpFilePath = iterator.next();
			if (FileDlpUtil.isDynamicDecrypt(dlpFilePath)) {
				logger.debug("对加密文件进行解密操作，操作路径:" + dlpFilePath);
				String newFilePath = replaceFileName(dlpFilePath);
				FileDlpUtil.decryptFile(dlpFilePath, newFilePath);
				AutoUpdateFile.remove(dlpFilePath, newFilePath);
			}
		}
	}

	public static boolean remove(String sourcePath, String targetPath) {
		File targetFile = new File(targetPath);

		if (targetFile.isFile()) {
			File sourceFile = new File(sourcePath);
			return sourceFile.delete();
		}

		return false;
	}

	public static String replaceFileName(String dlpFilePath) {
		String stuffix = dlpFilePath.substring(dlpFilePath.lastIndexOf("."),
				dlpFilePath.length());
		String fisrtpathName = dlpFilePath.substring(0,
				dlpFilePath.lastIndexOf("."));
		fisrtpathName = fisrtpathName + "_2013" + stuffix;
		return fisrtpathName;
	}

	public static void main1(String[] args) {
		// String dlpFilePath = "c:\\abx\\bac.txt";
		//
		// String abx = dlpFilePath.substring(dlpFilePath.lastIndexOf("\\"));
		// System.out.println(abx);

		System.out.println((int) 'a');

	}

}
