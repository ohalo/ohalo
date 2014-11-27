package cn.ohalo.stock.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import cn.ohalo.stock.LikeStock;
import cn.ohalo.stock.bundle.ConfigBundle;
import cn.ohalo.stock.entity.RegStockInfo;
import cn.ohalo.stock.entity.StabilityStock;
import cn.ohalo.stock.job.StockRecommend;
import cn.ohalo.stock.notice.AlertMsg;
import cn.ohalo.stock.rule.StockRuleDown;
import cn.ohalo.stock.rule.StockRuleUp;
import cn.ohalo.stock.util.CsvUtil;
import cn.ohalo.stock.util.StockUtil;
import cn.ohalo.stock.util.TestUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 深市数据链接：http://table.finance.yahoo.com/table.csv?s=000001.sz
 * 上市数据链接：http://table.finance.yahoo.com/table.csv?s=600000.ss
 * 
 * @author halo
 * 
 */
public class StockTest extends TestCase {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private DBCollection collection;

	private BufferedReader br;

	private StockUtil su;


	@Override
	protected void setUp() throws Exception {
		collection = TestUtil.initMongoSet("wanke_000002");
	}

	public void testPrint() {
		String msg = "你的股票%s查看sina的微操盘看一下亏本金额，购买股票代码：%s ,购买数量：%s ,购买金额："
				+ "%s (元),当前金额：%s (元)，计算亏本金额：%s";
		System.out.printf(msg, "已亏本,", "000002", 100, 100.00, 100.11, 1000.22);
	}

	public void initStockInfo() {
		if (su == null) {
			su = new StockUtil("http://quote.eastmoney.com/stocklist.html",
					"quotesearch");
		}
	}

	public void testCalcStock() {
		LikeStock linkStock = new LikeStock();
		System.out.println("需要多少价格抛售："
				+ linkStock.calcStockProfit("000002", 2000, 7.46, 160.00));
		System.out.println("需要多少价格抛售："
				+ linkStock.calcStockProfit("民和股份", 1200, 8.13, 100.00));
		System.out.println("需要多少价格抛售："
				+ linkStock.calcStockProfit("民和股份", 1200, 8.13, 100.00));
		System.out.println("需要多少价格抛售："
				+ linkStock.calcStockProfit("罗平锌电", 1300, 7.48, 100.00));
		System.out.println("需要多少价格抛售："
				+ linkStock.calcStockProfit("000990", 600, 9.38, 50.00));
	}

	public void testImport() throws IOException {
		collection.drop();
		List<DBObject> objs = TestUtil
				.importExcel("F:\\halo_internetdowunload\\table.xlsx");
		collection.insert(objs);
		System.out.println(collection.count());
	}

	public void testOverReadCsv() {
		try {
			br = new BufferedReader(new FileReader(
					"G:\\halo_users\\halo\\logs\\ohalo.stock.log.1"));

			String temp = "";
			while ((temp = br.readLine()) != null) {
				if (temp.indexOf("http://") > -1) {
					temp = temp.substring(temp.indexOf("http://"),
							temp.length());
					String stockCode = temp.substring(temp.indexOf("s=") + 2,
							temp.length() - 3);
					String teamOrg = temp.substring(temp.length() - 2);
					System.out.println("开始采集数据：" + temp + ",股票编码:" + stockCode
							+ ",所属机构：" + teamOrg);
					List<DBObject> list = CsvUtil.getCsv(stockCode, teamOrg);
					if (list == null || list.isEmpty()) {
						continue;
					}
					DBCollection collection1 = TestUtil.initMongoSet(stockCode);
					collection1.drop();
					collection1.insert(list);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testImportAllStock() {
		collection = TestUtil.initMongoSet("company_stock");
		DBCursor cursor = collection.find();

		ExecutorService excuter = new ThreadPoolExecutor(8, 100, 60000L,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(100000),
				new ThreadPoolExecutor.CallerRunsPolicy());

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			final String stockCode = (String) obj.get("stockCode");
			final String teamOrg = (String) obj.get("teamOrg");
			excuter.execute(new Runnable() {
				@Override
				public void run() {
					List<DBObject> list = CsvUtil.getCsv(stockCode, teamOrg);
					if (list == null || list.isEmpty()) {
						return;
					}
					DBCollection collection1 = TestUtil.initMongoSet(stockCode);
					collection1.drop();
					collection1.insert(list);
				}
			});
		}

		try {
			Thread.sleep(3200 * 60000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 根据一天收盘价格进行区间分配
	 */
	public void testQueryAll() {

		DBObject object = new BasicDBObject();
		object.put("closePrice", 1);
		DBCursor cursor = collection.find().sort(object);

		Map<String, Integer> total = new HashMap<String, Integer>();

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Double cp = Double.parseDouble(obj.get("closePrice").toString());
			String b = cp.intValue() + "";
			Integer cc = total.get(b) == null ? 0 : total.get(b);
			cc++;
			total.put(b, cc);
		}

		Set<String> keys = total.keySet();

		for (String string : keys) {
			System.out.println("key：" + string + ",value:" + total.get(string));
		}
	}

	/**
	 * 统计所有股票，近三年股价稳定在三个点之内的的股票
	 */
	public void testCalcStockData() {
		collection = TestUtil.initMongoSet("company_stock");
		DBCursor cursor = collection.find();

		List<DBObject> list = new ArrayList<DBObject>();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			final String stockCode = (String) obj.get("stockCode");
			Date startDate = TestUtil.getWantDate(-1, 0, 0);
			DBObject queryParams = new BasicDBObject("recordDate",
					new BasicDBObject("$gt", startDate));
			Map<Integer, Integer> datas = TestUtil.queryDataAll(stockCode,
					queryParams, "closePrice");
			if (datas.size() <= 1 && datas.size() > 0) {
				StabilityStock stock = new StabilityStock(stockCode, obj.get(
						"stockName").toString(), datas);
				list.add(stock.toDBObject());
			}
		}

		collection = TestUtil.initMongoSet("near_oneyear_stabilitystock");
		collection.drop();
		collection.insert(list);
	}

	/**
	 * 统计所有股票，计算最近一年的股票，涨幅在1快钱以内的
	 */
	public void testCalcStockSpace() {
		collection = TestUtil.initMongoSet("company_stock");
		DBCursor cursor = collection.find();

		List<DBObject> list = new ArrayList<DBObject>();

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			final String stockCode = (String) obj.get("stockCode");
			Date startDate = TestUtil.getWantDate(-1, 0, 0);
			DBObject queryParams = new BasicDBObject("recordDate",
					new BasicDBObject("$gt", startDate));
			DBCollection collection1 = TestUtil.initMongoSet(stockCode);
			DBObject object = new BasicDBObject();
			object.put("closePrice", 1);
			DBCursor cursor1 = collection1.find(queryParams).sort(object);
			int a = 0;
			while (cursor1.hasNext()) {
				DBObject obj1 = cursor1.next();
				Double openPrice = Double.parseDouble(obj1.get("openPrice")
						.toString());
				Double colsePrice = Double.parseDouble(obj1.get("closePrice")
						.toString());
				if (Math.abs(openPrice - colsePrice) <= 0.5
						&& Math.abs(openPrice - colsePrice) > 0.1) {
					a++;
				}
			}
			if (a > 1 * 365 / 2) {
				DBObject dbObj = new BasicDBObject();
				dbObj.put("stockCode", stockCode);
				dbObj.put("stockName", obj.get("stockName").toString());
				list.add(dbObj);
			}
		}

		collection = TestUtil
				.initMongoSet("near_lthalfyungtonemao_stabilitystock");
		collection.drop();
		collection.insert(list);
	}

	public void testABC() {
		double a = 1.0;
		double b = 2.0;
		System.out.println(Math.abs(a - b));
	}

	/**
	 * 查询最近三年的数据记录，做信息统计
	 * 
	 * @throws ParseException
	 */
	public void testQueryThreeYeahsData() throws ParseException {

		System.out.println(sdf.parse("2010-01-01"));

		DBObject object = new BasicDBObject();
		object.put("recordDate",
				new BasicDBObject("$gt", sdf.parse("2010-01-01")));
		DBObject object1 = new BasicDBObject();
		object1.put("closePrice", 1);
		DBCursor cursor = collection.find(object).sort(object1);

		Map<String, Integer> total = new HashMap<String, Integer>();

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Double cp = Double.parseDouble(obj.get("closePrice").toString());
			String b = cp.intValue() + "--" + (cp.intValue() + 1);
			Integer cc = total.get(b) == null ? 0 : total.get(b);
			cc++;
			total.put(b, cc);

		}

		Set<String> keys = total.keySet();

		for (String string : keys) {
			System.out.println("key：" + string + ",value:" + total.get(string));
		}
	}

	public void testyear() {

		// 关于得到上一年，下一年的相关时间
		Calendar ca1 = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = ca1.getTime();
		// 得到当前时间
		System.out.println(sdf.format(d1));

		// 如何得到明年的今天~
		int year = ca1.get(Calendar.YEAR);
		System.out.println("得到当前年：" + year);
		ca1.set(Calendar.YEAR, year - 3);
		System.out.println(sdf.format(ca1.getTime()));

		// 那么得到后年的今天， 明年的下个月的今天皆可以了，至于得到明年的后天得在天数上做的文章，如判断当月是否为闰月
		// 天数是否为30 或者 31 之类
	}

	public void testa() {
		Double d = 1.8232;
		System.out.println(d.intValue());
	}

	public void testUrl() {
		// 关于得到上一年，下一年的相关时间
		Calendar ca1 = Calendar.getInstance();
		// 如何得到明年的今天~
		int year = ca1.get(Calendar.YEAR);
		int month = ca1.get(Calendar.MONTH);
		int day = ca1.get(Calendar.DAY_OF_MONTH);

		ca1.set(Calendar.DAY_OF_MONTH, day - 5);
		String yahooStockUrl = ConfigBundle.getString("yahoo_stock_url_day");

		yahooStockUrl = MessageFormat.format(yahooStockUrl, "000002.ss",
				ca1.get(Calendar.MONTH), ca1.get(Calendar.DAY_OF_MONTH),
				ca1.get(Calendar.YEAR) + "", month, day, year + "");
		System.out.println(yahooStockUrl);
	}

	public void testMax() {
		System.out.println(Math.round(10000 / 1.05));
	}

	public void testcalcDown() {
		StockRecommend sr = new StockRecommend();
		sr.buyRecommend(new StockRuleDown());
		AlertMsg as = AlertMsg.getInstance();
		as.printMsg();

		try {
			Thread.sleep(24 * 60 * 60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void testcalcUp() {
		final List<RegStockInfo> infos = new ArrayList<RegStockInfo>();
		infos.add(new RegStockInfo("002168", "sz", 1300, 7.38));// 江苏宏宝
		//infos.add(new RegStockInfo("600999", "sh", 600, 9.92));// 廊坊发展
		//infos.add(new RegStockInfo("600999", "sh", 600, 10.00));// 江苏索普
		StockRecommend sr = new StockRecommend();
		sr.recommend(infos, new StockRuleUp());
		AlertMsg as = AlertMsg.getInstance();
		as.printMsg();
		try {
			Thread.sleep(24 * 60 * 60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
