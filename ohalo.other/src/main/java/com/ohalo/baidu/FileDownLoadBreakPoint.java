package com.ohalo.baidu;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileDownLoadBreakPoint {
	public static void main(String[] args) {
		FileDownLoadBreakPoint fdbp = new FileDownLoadBreakPoint();
		try {
			fdbp.breakPointDownLoad("E:/download/other/breakpoint/1.txt",
					"E:/download/other/breakpoint/3.txt");
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
	public void breakPointDownLoad(String sourceFilePath, String savaFilePath)
			throws IOException {// 复制文件
		File c = new File(sourceFilePath);// 源文件
		File s = new File(savaFilePath);// 下载后的文件
		if (!c.exists()) {
			System.out.println("文件" + sourceFilePath + "不存在");
			return;
		}
		if (!c.isFile()) {
			System.out.println(sourceFilePath + "不是文件，马上对文件夹" + sourceFilePath
					+ "进行复制");
			return;
		}
		byte len[] = new byte[5];
		RandomAccessFile oldAccessFile = new RandomAccessFile(sourceFilePath,
				"r");
		RandomAccessFile accessFile = new RandomAccessFile(savaFilePath, "rwd");
		System.out.println(accessFile.length());

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
		System.out.println("文件" + savaFilePath + "复制成功");
		accessFile.close();
	}
}
