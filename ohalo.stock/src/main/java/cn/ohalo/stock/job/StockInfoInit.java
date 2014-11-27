package cn.ohalo.stock.job;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.ohalo.stock.entity.CompanyStock;
import cn.ohalo.stock.file.StockOprInFile;
import cn.ohalo.stock.socket.SinaStockClient;

public class StockInfoInit {

	private StockOprInFile soif = StockOprInFile.getInstance();

	private SinaStockClient client = SinaStockClient.getInstance();

	private StockInfoInit() {

	}

	private static StockInfoInit init = new StockInfoInit();

	public static StockInfoInit getInstance() {
		return init;
	}

	private void initStockCodes() {
		List<CompanyStock> cstocks = soif.queryAllStock();
		if (cstocks == null || cstocks.isEmpty()) {
			soif.insertAllStock("http://quote.eastmoney.com/stocklist.html",
					"quotesearch");
			initStockCodes();
			return;
		}
		System.out.println("共有多少只股票：" + cstocks.size());
		List<String> stockCodes = new ArrayList<String>();
		for (Iterator<CompanyStock> iterator = cstocks.iterator(); iterator
				.hasNext();) {
			CompanyStock cs = iterator.next();
			String newName = ("ss".equals(cs.getTeamOrg()) ? "sh" : "sz")
					+ cs.getStockCode();
			stockCodes.add(newName);
		}
		client.setStockCodes(stockCodes);
	}

	public void initStockPrice() {
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
