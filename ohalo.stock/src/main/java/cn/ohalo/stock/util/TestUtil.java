package cn.ohalo.stock.util;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.time.TimeSeries;

import cn.ohalo.stock.entity.YaHooStockEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class TestUtil {

	private static Mongo mg = null;
	private static DB db;
	private static DBCollection collection;
	private static String dbName = "temp";

	private static Map<String, TimeSeries> yearCharts = new HashMap<String, TimeSeries>();

	@SuppressWarnings("deprecation")
	public static DBCollection initMongoSet(String collectionName) {
		if (mg == null) {
			try {
				// mg = new Mongo();
				mg = new Mongo("localhost", 27017);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			}
		}
		if (db == null) {
			// 获取temp DB；如果默认没有创建，mongodb会自动创建
			db = mg.getDB(dbName);
		}

		collection = db.getCollection(collectionName);
		return collection;
	}

	public static Map<String, TimeSeries> getJFreeCharts() {
		return yearCharts;
	}

	public static void destory() {
		if (mg != null)
			mg.close();
		mg = null;
		db = null;
		collection = null;
		System.gc();
	}

	/**
	 * 根据一天收盘价格进行区间分配
	 * 
	 * 取出所有数据，然后拿出最大值（最大值保留，最大值说明是频繁发生价格变动区域，排除最小值）和最小值，做不稳定股票价格排除，然后做近期股票市场分析
	 * 
	 * 现万科股票交易次数是5946次，先排除开盘价格小于100次的，因价格不稳定，所以排除
	 */
	public static Map<Integer, Integer> queryDataAll(String collectionName,
			DBObject queryParams, String orderParam) {
		collection = TestUtil.initMongoSet(collectionName);

		DBObject object = new BasicDBObject();
		object.put(orderParam, 1);
		DBCursor cursor = collection.find(queryParams).sort(object);

		Map<Integer, Integer> total = new LinkedHashMap<Integer, Integer>();

		// Map<String, TimeSeries> yearCharts = new HashMap<String,
		// TimeSeries>();

		System.out.println("打印最近三年的总的开盘天数：" + cursor.size());

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Double cp = Double.parseDouble(obj.get(orderParam).toString());
			Integer cc = total.get(cp.intValue()) == null ? 0 : total.get(cp
					.intValue());
			/*
			 * if (cp.intValue() <= cp && cp <= (cp.intValue() + 1)) { //
			 * TimeSeries series = new TimeSeries("按年来分", Year.class); if
			 * (yearCharts.get(b) != null) { TimeSeries series =
			 * yearCharts.get(b); series.add(new Day((Date)
			 * obj.get("recordDate")), cp); yearCharts.put(b, series); } else {
			 * TimeSeries series = new TimeSeries(b + "股票开价走向图", Day.class);
			 * series.add(new Day((Date) obj.get("recordDate")), cp);
			 * yearCharts.put(b, series); } }
			 */
			cc++;
			total.put(cp.intValue(), cc);
			// System.out.println("开盘价格：" + cp + ",开盘时间："
			// + sdf.format(obj.get("recordDate")));
		}

		Set<Integer> keys = total.keySet();

		int meanValue = cursor.size()
				/ ((keys == null || keys.size() == 0) ? 1 : keys.size());
		System.out.println("开盘价平均值计算：" + meanValue);
		for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
			Integer string = iterator.next();
			if (total.get(string) < meanValue) {
				System.out.println("key：" + string + ",value:"
						+ total.get(string));
				// iterator.remove();
			}
		}

		return total;
	}

	/**
	 * 获取你想要的时间
	 * 
	 * @param byear
	 *            +1，就是+一年，如果今年是2013年，那加一年就是2014年
	 * @param bmonth
	 *            同上
	 * @param bday
	 *            同上
	 * @return
	 */
	public static Date getWantDate(int byear, int bmonth, int bday) {
		// 关于得到上一年，下一年的相关时间
		Calendar ca1 = Calendar.getInstance();
		// 如何得到明年的今天~
		int year = ca1.get(Calendar.YEAR);
		int date = ca1.get(Calendar.MONTH);
		int day = ca1.get(Calendar.DAY_OF_MONTH);
		ca1.set(Calendar.YEAR, year + byear);
		ca1.set(Calendar.MONTH, date + bmonth);
		ca1.set(Calendar.DAY_OF_MONTH, day + bday);
		// System.out
		// .println("得到当前年：" + year + ",时间:" + sdf.format(ca1.getTime()));
		return ca1.getTime();
	}

	@SuppressWarnings("unused")
	public static List<DBObject> importExcel(String path) throws IOException {

		OPCPackage openPackage = XSSFWorkbook.openPackage(path);

		// 返回校验结果
		String res = "";
		// 创建一个Excel book
		XSSFWorkbook wb = new XSSFWorkbook(openPackage);
		// 获取第一个sheet页
		XSSFSheet sheet = wb.getSheetAt(0);

		System.out.println(sheet.getLastRowNum());
		List<DBObject> dbObject = new ArrayList<DBObject>();

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			XSSFRow row = sheet.getRow(i);

			XSSFCell cell0 = row.getCell(0);
			XSSFCell cell1 = row.getCell(1);
			XSSFCell cell2 = row.getCell(2);
			XSSFCell cell3 = row.getCell(3);
			XSSFCell cell4 = row.getCell(4);
			XSSFCell cell5 = row.getCell(5);
			XSSFCell cell6 = row.getCell(6);

			YaHooStockEntity entity = new YaHooStockEntity(
					cell0.getDateCellValue(), cell1.getNumericCellValue(),
					cell2.getNumericCellValue(), cell3.getNumericCellValue(),
					cell4.getNumericCellValue(), cell5.getNumericCellValue(),
					cell6.getNumericCellValue());

			dbObject.add(entity.toDBObject());
		}

		return dbObject;
	}
}
