package cn.ohalo.stock.test;

import java.util.List;

import junit.framework.TestCase;
import cn.ohalo.stock.entity.RegStockInfo;
import cn.ohalo.stock.service.RegStockService;

public class RegStockTest extends TestCase {

	RegStockService service;

	@Override
	protected void setUp() throws Exception {
		service = RegStockService.getInstance();
	}

	public void testAddRegStock() {
		RegStockInfo rs = new RegStockInfo("0000002", "sh", 10000, 6.89);
		service.insert(rs);
		List<RegStockInfo> rsinfos = service.queryAll();
		System.out.println(rsinfos.get(0));
	}
}
