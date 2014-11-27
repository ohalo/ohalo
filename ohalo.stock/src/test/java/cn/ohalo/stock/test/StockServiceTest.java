package cn.ohalo.stock.test;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import cn.ohalo.stock.entity.StockInfo;
import cn.ohalo.stock.service.StockInfoService;

public class StockServiceTest extends TestCase {

	StockInfoService stockService;

	@Override
	protected void setUp() throws Exception {
		stockService = StockInfoService.getInstance();
	}

	public void testStockQuery() {
		StockInfo rs = new StockInfo("000002", 1.002, new Date(), 100.00,
				StockInfo.BUY_IN_TYPE);
		stockService.insert(rs);
		List<StockInfo> rsinfos = stockService.queryAll();
		System.out.println(rsinfos.get(0));
	}
}
