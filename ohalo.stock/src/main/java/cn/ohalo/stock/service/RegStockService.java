package cn.ohalo.stock.service;

import cn.ohalo.stock.cache.StockCache;
import cn.ohalo.stock.entity.RegStockInfo;

public class RegStockService extends StockCache<String, RegStockInfo> {

	private static String REG_STOCK_CACHE_NAME = "reg_scache";

	private static RegStockService rss;

	private RegStockService() {
		setCacheName(REG_STOCK_CACHE_NAME);
	}

	public static RegStockService getInstance() {
		if (rss == null) {
			rss = new RegStockService();
		}
		return rss;
	}
}
