package com.ohalo.test.readOnline;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ohalo.test.readOnline.entity.FileEntity;

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
 * @since 2013-5-7
 * @version 1.0
 */
@SuppressWarnings("unused")
public class FileUtils {

	private static Log logger = LogFactory.getLog(FileUtils.class);

	private static String localSavePath = "F:\\halo_cloudFile";

	private static String remoteFilefixedPath = "F:\\halo_sourcecode";

	private String remoteFilePath;

	private static Set<FileEntity> filepath = new HashSet<FileEntity>();

	public void getPathFiles(String path) {
		if (StringUtils.isBlank(path)) {
			logger.info("path is null," + path);
			return;
		}

		File file = new File(path);

		if (!file.isDirectory()) {
			FileEntity entity = new FileEntity();
			entity.setName(file.getName());
			entity.setPath(file.getPath());
			entity.setLength(file.length());
			filepath.add(entity);
			return;
		}

		File[] fils = file.listFiles();

		for (File file2 : fils) {
			getPathFiles(file2.getPath());
		}
	}

	public void initLoaclFileInfo() {
		getPathFiles(localSavePath);
	};

	public FileEntity searchFile(FileEntity entity) {
		if (filepath.contains(entity)) {
			return entity;
		}
		return null;

	}

	public boolean checkFilesIsSame(FileEntity entity) {
		initLoaclFileInfo();
		FileEntity entity1 = searchFile(entity);
		if (entity1 != null) {
			logger.info("改文件已经存在");
			return true;
		}
		return false;
	}

	public void receiveFile(String filepath) throws IOException {

		File file = new File(filepath);
		FileEntity entity = new FileEntity();
		entity.setLength(file.length());
		entity.setName(file.getName());
		entity.setPath(file.getPath());

		boolean flag = checkFilesIsSame(entity);

		if (!flag)
			return;
		byte len[] = new byte[1024 * 200];

		@SuppressWarnings("resource")
		RandomAccessFile oldAccessFile = new RandomAccessFile(filepath, "r");

		// oldAccessFile.seek(accessFile.length());

		// int length = old.read(len);
		int length = oldAccessFile.read(len);

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

	public String getRemoteFilePath() {
		return remoteFilePath;
	}

	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}
}
