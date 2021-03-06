package org.ohalo.resource.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * 
 * 
 * <pre>
 * 功能：PreparatorUtil.java 文件穿透
 * 作者：Z.halo
 * 日期：2013-6-25下午6:03:17
 * </pre>
 */
public class PreparatorUtil {
	// 文件类型集合
	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	/**
	 * 无参构造
	 * 
	 */
	private PreparatorUtil() {
	}

	/**
	 * 静态模块 初始化文件类型信息
	 */
	static {
		getAllFileType(); // 初始化文件类型信息
	}

	/**
	 * @Title: getAllFileType
	 * @Description:(常见文件头信息)
	 * @param 设定文件
	 * @returnvoid 返回类型
	 * @throws
	 * @date 2012-12-20 上午10:34:31
	 */
	private static void getAllFileType() {
		FILE_TYPE_MAP.put("jpg", "FFD8FF"); // JPEG (jpg)
		FILE_TYPE_MAP.put("png", "89504E47"); // PNG (png)
		FILE_TYPE_MAP.put("gif", "47494638"); // GIF (gif)
		FILE_TYPE_MAP.put("tif", "49492A00"); // TIFF (tif)
		FILE_TYPE_MAP
				.put("bmp",
						"89504E470D0A1A0A0000000D4948445200000060000000600806000000E2987738000000017352474200AECE1CE900000004"); // Windows
																																	// Bitmap
																																	// (bmp)
		FILE_TYPE_MAP.put("dwg", "41433130"); // CAD (dwg)
		FILE_TYPE_MAP.put("html", "68746D6C3E"); // HTML (html)
		FILE_TYPE_MAP.put("htm", "3C21444F435459504520"); // HTML (html)
		FILE_TYPE_MAP.put("rtf", "7B5C727466"); // Rich Text Format (rtf)
		FILE_TYPE_MAP.put("xml", "3C3F786D6C");
		FILE_TYPE_MAP.put("zip", "504B03040A00000000009");
		FILE_TYPE_MAP.put("rar", "52617221");
		FILE_TYPE_MAP.put("psd", "38425053"); // Photoshop (psd)
		FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A"); // Email
																	// [thorough
																	// only]
																	// (eml)
		FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F"); // Outlook Express (dbx)
		FILE_TYPE_MAP.put("pst", "2142444E"); // Outlook (pst)
		FILE_TYPE_MAP
				.put("xls",
						"D0CF11E0A1B11AE1000000000000000000000000000000003E000300FEFF0900060000000000000000000000010000000100"); // MS
																																	// Word
		FILE_TYPE_MAP.put("xlsx", "504B030414000600080000002100C8A3");
		FILE_TYPE_MAP
				.put("doc",
						"D0CF11E0A1B11AE1000000000000000000000000000000003E000300FEFF09000600000000000000000000004E0000005600");
		FILE_TYPE_MAP.put("docx", "504B030414000600080000002100729");
		FILE_TYPE_MAP.put("pptx", "504B03041400060008000000210036F7");
		FILE_TYPE_MAP
				.put("ppt",
						"D0CF11E0A1B11AE1000000000000000000000000000000003E000300FEFF0900060000000000000000000000020000000100");
		FILE_TYPE_MAP.put("mdb", "5374616E64617264204A"); // MS Access (mdb)
		FILE_TYPE_MAP.put("wpd", "FF575043"); // WordPerfect (wpd)
		FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("pdf", "255044462D312E"); // Adobe Acrobat (pdf)
		FILE_TYPE_MAP.put("qdf", "AC9EBD8F"); // Quicken (qdf)
		FILE_TYPE_MAP.put("pwl", "E3828596"); // Windows Password (pwl)
		FILE_TYPE_MAP.put("wav", "57415645"); // Wave (wav)
		FILE_TYPE_MAP.put("avi", "41564920");
		FILE_TYPE_MAP.put("ram", "2E7261FD"); // Real Audio (ram)
		FILE_TYPE_MAP.put("rm", "2E524D46"); // Real Media (rm)
		FILE_TYPE_MAP.put("mpg", "000001BA"); //
		FILE_TYPE_MAP.put("mov", "6D6F6F76"); // Quicktime (mov)
		FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); // Windows Media (asf)
		FILE_TYPE_MAP.put("mid", "4D546864"); // MIDI (mid)
		FILE_TYPE_MAP.put("sql", "73656C656374200D0");
		FILE_TYPE_MAP.put("txt", "73656C6563742032303");
		FILE_TYPE_MAP.put("java", "7061636B61676520636F6D2E");
	}

	/**
	 * 将输入字节流转化为16为的数据
	 * 
	 * @param b
	 * @return
	 */
	public static String getFileHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();
		if (b == null || b.length <= 0) {
			return null;
		}
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 判断是否已经存在已经设定的文件类型
	 * 
	 * @param b
	 * @return String
	 */
	public static String getFileTypeByStream(byte[] b) {
		String filetypeHex = String.valueOf(getFileHexString(b));
		// 将map转换为可遍历的set
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP
				.entrySet().iterator();
		// 遍历map集合中的内容，来判断是否已经存在已定义好的后缀文件格式中
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			String fileTypeHexValue = entry.getValue();
			if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * 判断文件类型
	 * 
	 * @param file
	 * @return
	 */
	public static String getfiletypeByFile(File file) {
		String filetype = null;
		byte[] b = new byte[50];
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			is.read(b);
			// 判断文件的类型
			filetype = getFileTypeByStream(b);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return filetype;
	}

	/**
	 * 生成八位UUID 随机生成
	 * 
	 * @return
	 */
	public static String getUUIDRandom() {
		Random rd = new Random();
		String res = Integer.toHexString(rd.nextInt(16)).toUpperCase()
				+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
				+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
				+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
				+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
				+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
				+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
				+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
				+ Integer.toHexString(rd.nextInt(16)).toUpperCase()
				+ Integer.toHexString(rd.nextInt(16)).toUpperCase();
		return res;
	}
}