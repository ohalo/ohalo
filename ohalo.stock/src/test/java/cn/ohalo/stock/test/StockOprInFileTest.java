package cn.ohalo.stock.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import junit.framework.TestCase;
import cn.ohalo.stock.entity.CompanyStock;
import cn.ohalo.stock.file.StockOprInFile;
import cn.ohalo.stock.socket.SinaStockClient;

public class StockOprInFileTest extends TestCase {
	private StockOprInFile soif;

	SinaStockClient client;

	@Override
	protected void setUp() throws Exception {
		soif = StockOprInFile.getInstance();
		client = SinaStockClient.getInstance();

	}

	public void testInsertCompanyStockInFile() throws IOException {
		soif.insertAllStock("http://quote.eastmoney.com/stocklist.html",
				"quotesearch");
	}

	public void testJson() {
		// String str =
		// "{'stockCode'='201000', 'stockName'='R003', 'linkAddr'='http://quote.eastmoney.com/sh201000.html','teamOrg':'ss'}";
		CompanyStock cstock = null;// TypeUtils.castToJavaBean(str,
									// CompanyStock.class);
		// System.out.println(cstock.getStockCode());

		cstock = new CompanyStock();
		cstock.setLinkAddr("bbbac");
		cstock.setStockCode("aaads");
		cstock.setStockName("asdasda");
		cstock.setTeamOrg("ha");

		String str = JSON.toJSONString(cstock);
		System.out.println(str);

		String str1 = "{'stockCode':'aaads', 'stockName':'asdasda', 'linkAddr':'bbbac','teamOrg':'ha'}";
		cstock = JSON.parseObject(str1, CompanyStock.class);
		System.out.println(cstock.getLinkAddr());
	}

	public void initStockCodes() {
		List<CompanyStock> cstocks = soif.queryAllStock();
		System.out.println(cstocks.size());
		List<String> stockCodes = new ArrayList<String>();
		for (Iterator<CompanyStock> iterator = cstocks.iterator(); iterator
				.hasNext();) {
			CompanyStock cs = iterator.next();
			String newName = ("ss".equals(cs.getTeamOrg()) ? "sh" : "sz")
					+ cs.getStockCode();
			// System.out.println(newName);
			stockCodes.add(newName);
		}
		client.setStockCodes(stockCodes);
	}

	public void testInsertcompanyStock() {
		initStockCodes();
		List<CompanyStock> cstocks = soif.queryAllStock();
		Map<String, Double> stockPrice = client.getCurrentPrices();
		List<CompanyStock> cstockss = new ArrayList<CompanyStock>();
		for (Iterator<CompanyStock> iterator = cstocks.iterator(); iterator
				.hasNext();) {
			CompanyStock companyStock = iterator.next();
			String newName = ("ss".equals(companyStock.getTeamOrg()) ? "sh"
					: "sz") + companyStock.getStockCode();
			companyStock.setCurrentPrice(stockPrice.get(newName));
			cstockss.add(companyStock);
		}
		soif.insertStock(cstockss);
	}
}
