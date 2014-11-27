package com.ohalo.test.readOnline;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <pre>
 * ###description###
 * 
 * 
 * 
 * #################
 * </pre>
 * 
 * @author Z.D.Halo
 * @since 2013-4-30
 * @version 1.0
 */
public class FileDownLoadBreakPoint {

	private static Log logger = LogFactory.getLog(FileDownLoadBreakPoint.class);

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		FileDownLoadBreakPoint fdbp = new FileDownLoadBreakPoint();
		try {
			fdbp.breakPointDownLoad(
					"F:/halo_window/文职—职能、（营运）运作2013年第一季度转正考试复习资料/职能、（营运）运作学习复习资料归档.xlsx",
					"F:/halo_window/文职—职能、（营运）运作2013年第一季度转正考试复习资料/3.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see 断点复制
	 * @param sourceFilePath
	 * @param savaFilePath
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void breakPointDownLoad(String sourceFilePath,
			String savaFilePath) throws IOException {// 复制文件
		File c = new File(sourceFilePath);// 源文件
		File s = new File(savaFilePath);// 下载后的文件
		if (!c.exists()) {
			logger.info("文件" + sourceFilePath + "不存在");
			return;
		}
		if (!c.isFile()) {
			logger.info(sourceFilePath + "不是文件，马上对文件夹" + sourceFilePath
					+ "进行复制");
			return;
		}
		byte len[] = new byte[1024 * 200];
		RandomAccessFile oldAccessFile = new RandomAccessFile(sourceFilePath,
				"r");
		RandomAccessFile accessFile = new RandomAccessFile(savaFilePath, "rwd");
		logger.info(accessFile.length());

		if (!s.exists()) {
			accessFile.seek(0);
		} else {
			accessFile.seek(accessFile.length());
			oldAccessFile.seek(accessFile.length());
		}
		// int length = old.read(len);
		int length = oldAccessFile.read(len);
		while (length > 0) {
			accessFile.write(len, 0, length);
			// length = old.read(len);
			length = -1;
		}
		logger.info("文件" + savaFilePath + "复制成功");
		accessFile.close();
	}
}
