package cn.ohalo.stock.service;

import java.util.List;

import cn.ohalo.stock.bundle.ConfigBundle;
import cn.ohalo.stock.cache.StockCache;
import cn.ohalo.stock.entity.StockInfo;
import cn.ohalo.stock.file.StockOprInFile;
import cn.ohalo.stock.notice.MsgNotice;

public class StockInfoService extends StockCache<String, StockInfo> implements
		MsgNotice<String, StockInfo> {

	private static String STOCK_INFO_CACHE_NAME = "s_info_cache";
	
	private StockOprInFile stockOpfile;

	private static StockInfoService rss;

	private StockInfoService() {
		setCacheName(STOCK_INFO_CACHE_NAME);
		stockOpfile = StockOprInFile.getInstance();
		stockOpfile.setAllStockfileAddress(ConfigBundle.getString("buy_stock_fileAddress"));
	}

	public static StockInfoService getInstance() {
		if (rss == null) {
			rss = new StockInfoService();
		}
		return rss;
	}

	@Override
	public void insert(StockInfo v) {
		super.insert(v);
	}
	
	public void insertToFile(){
		List<StockInfo> infos =	queryAll();
		stockOpfile.insertStock(infos);
	}
	
	public void insertToFile(List<StockInfo> infos){
		stockOpfile.insertStock(infos);
	}

	@Override
	public void printMsg() {

	}

}
