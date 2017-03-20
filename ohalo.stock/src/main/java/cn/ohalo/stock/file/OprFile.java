package cn.ohalo.stock.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

public class OprFile<T> {

	private static Log logger = LogFactory.getLog(OprFile.class);

	private String file_address = "";

	private String charsetName = "UTF-8";

	public OprFile(String fileAddress, String charsetName) {
		this.file_address = fileAddress;
		this.charsetName = charsetName;
	}

	public void insert(List<T> stcoks) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(file_address)), charsetName));
			for (Iterator<T> iterator = stcoks.iterator(); iterator.hasNext();) {
				T dbObject = iterator.next();
				bw.append(dbObject.toString()).append("\r\n");
			}
		} catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}

	public List<T> queryAll(Class<T> clazz) {
		List<T> cstocks = new ArrayList<T>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(file_address)), charsetName));
			String str = null;
			while ((str = br.readLine()) != null) {
				try {
					T cstock = JSON.parseObject(str, clazz);
					cstocks.add(cstock);
				} catch (Exception e) {
					logger.error("打印无法序列化的字符串信息：" + str, e);
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return cstocks;
	}
}
