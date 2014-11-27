package com.ohalo.search.file.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ohalo.search.write.WriteFileInfo;

/**
 * 
 * @author Z.Halo
 * 
 */
public class HaloFileUtils {

	private static Set<String> jsStore = new HashSet<String>(10000000);

	private static String[] stuffix = new String[] { ".js", ".xml",
			"Action.java", ".jsp", "Entity.java", ".png", ".css", ".gif",
			".ico", ".jpg" };

	/** 原路径 **/
	private static String ySrcPath = "E:\\halo_workspace\\DPAP-framework";

	private static String targetPath = "E:\\halo.ui.workspace\\DPAP-framework";

	private static Log logger = LogFactory.getLog(WriteFileInfo.class);

	/**
	 * 
	 * <pre>
	 * 方法体说明：查询文件路径
	 * 作者：赵辉亮
	 * 日期：2013-5-6. 下午3:02:33
	 * </pre>
	 * 
	 * @param file
	 */
	public static void searchFilePath(File file, String[] stuffix) {
		if (file == null) {
			return;
		}

		if (!file.isDirectory()) {
			for (int i = 0; i < stuffix.length; i++) {
				if (file.getName().endsWith(stuffix[i])) {
					jsStore.add(file.getPath());
					break;
				} else {
					logger.debug("无效地址路径：" + file.getPath());
				}
			}
			return;
		}

		File files[] = file.listFiles();

		for (File f : files) {
			searchFilePath(f, stuffix);
		}
	}

	public static Set<String> getFileStore() {
		return jsStore;
	}

	public static void main(String[] args) {
		HaloFileUtils.searchFilePath(new File(ySrcPath), stuffix);
		for (Iterator<String> iterator = jsStore.iterator(); iterator.hasNext();) {
			String strPath = iterator.next();

			String path = strPath
					.substring(ySrcPath.length(), strPath.length());
			path = targetPath + path;
			File file = new File(path);
			if (file.isFile() && !path.endsWith(".js")
					&& !path.endsWith(".jsp") && !path.endsWith("struts.xml")) {
				continue;
			}
			try {
				FileUtils.copyFile(new File(strPath), new File(path));
			} catch (IOException e) {
				logger.error("出错原路径:" + strPath + ",出错目标路径：" + path, e);
			}

		}

	}

	public static void main1(String[] args) {

		String strPath = "E:\\halo_workspace\\fssc-parent\\fssc-claim\\src\\main\\java\\com\\deppon\\fssc\\module\\accounting\\server\\action";

		String path = strPath.substring(
				"E:\\halo_workspace\\fssc-parent".length(), strPath.length());

		File file = new File(strPath);
		if (file != null) {
			System.out.println(file.isFile());
		}

		System.out.println(targetPath + path);

	}
}
