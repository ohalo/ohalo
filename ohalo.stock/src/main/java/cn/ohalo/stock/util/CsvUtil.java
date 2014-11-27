package cn.ohalo.stock.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ohalo.stock.entity.YaHooStockEntity;

import com.mongodb.DBObject;

/**
 * 读取csv文件，提取需要的数据
 * 
 * @author halo
 * 
 */
public class CsvUtil {

	private static String filename = null;

	private static Log logger = LogFactory.getLog(CsvUtil.class);

	private static BufferedReader bufferedreader = null;

	public static List<DBObject> getCsv(String filename) {

		List<DBObject> entitys = new ArrayList<DBObject>();
		CsvUtil.filename = filename;
		try {
			return getCsv(new FileInputStream(new File(CsvUtil.filename)));
		} catch (FileNotFoundException e) {
			logger.error("未找到文件,文件地址：" + filename, e);
		}
		return entitys;
	}

	public static List<DBObject> getCsv(InputStream is) {
		List<DBObject> entitys = new ArrayList<DBObject>();
		try {
			bufferedreader = new BufferedReader(new InputStreamReader(is));
			String stemp;
			while ((stemp = bufferedreader.readLine()) != null) {
				if (stemp.indexOf("Date") > -1) {
					continue;
				}
				entitys.add(covertEntity(stemp));
			}
		} catch (FileNotFoundException e) {
			logger.error("未读取到文件!", e);
		} catch (IOException e) {
			logger.error("读取到文件失败!", e);
		}
		return entitys;
	}

	/**
	 * 
	 * @return
	 */
	public static DBObject covertEntity(String cloumnValue) {
		String[] str = cloumnValue.split(",");
		YaHooStockEntity entity = new YaHooStockEntity();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			entity.setRecordDate(sdf.parse(str[0].trim()));
			entity.setOpenPrice(Double.parseDouble(str[1].trim()));
			entity.setHighPrice(Double.parseDouble(str[2].trim()));
			entity.setLowPrice(Double.parseDouble(str[3].trim()));
			entity.setClosePrice(Double.parseDouble(str[4].trim()));
			entity.setVolumePrice(Double.parseDouble(str[5].trim()));
			entity.setAdjClosePrice(Double.parseDouble(str[6].trim()));
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("空指针异常，问题参数:" + str[0] + "," + Arrays.toString(str));
		} catch (NumberFormatException e) {
			logger.error(
					"转化数字出现问题，问题参数:" + str[0] + "," + Arrays.toString(str), e);
		} catch (ParseException e) {
			logger.error("转化日期出现问题，问题参数:" + str[0] + "," + Arrays.toString(str));
		}

		return entity.toDBObject();
	}

	public static List<DBObject> getCsv(String stockCode, String teamOrg) {

		String urlStr = "http://table.finance.yahoo.com/table.csv?s="
				+ stockCode + "." + teamOrg;
		List<DBObject> entitys = null;
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			entitys = getCsv(conn.getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("读取文件失败：" + urlStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("读取文件失败：" + urlStr);
		}
		return entitys;
	}

	public static List<DBObject> getCsvbyUrl(String urlStr) {
		List<DBObject> entitys = null;
		try {
			URL url = new URL(urlStr);
			entitys = getCsv(url);
		} catch (MalformedURLException e) {
			logger.error("读取文件失败：" + urlStr);
		}
		return entitys;
	}

	public static List<DBObject> getCsv(URL url) {
		List<DBObject> entitys = null;
		try {
			URLConnection conn = url.openConnection();
			entitys = getCsv(conn.getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("读取文件失败：" + url.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("读取文件失败：" + url.toString());
		}
		return entitys;
	}

	public static void main(String[] args) {
		// List<YaHooStockEntity> entitys = CsvUtil
		// .getCsv("F:\\halo_internetdowunload\\table.csv");

		// try {
		// URL url = new URL(
		// "http://table.finance.yahoo.com/table.csv?s=000001.sz");
		// URLConnection conn = url.openConnection();
		// List<DBObject> entitys = getCsv(conn.getInputStream());
		// System.out.println(entitys.get(0).toString());
		// } catch (MalformedURLException e) {
		// logger.error("", e);
		// } catch (IOException e) {
		// logger.error("", e);
		// }

		// try {
		// System.out.println(sdf.parse("2010-08-06"));
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
