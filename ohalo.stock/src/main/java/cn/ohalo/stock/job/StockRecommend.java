package cn.ohalo.stock.job;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.ohalo.stock.LikeStock;
import cn.ohalo.stock.bundle.ConfigBundle;
import cn.ohalo.stock.entity.CompanyStock;
import cn.ohalo.stock.entity.RegStockInfo;
import cn.ohalo.stock.entity.StockInfo;
import cn.ohalo.stock.file.StockOprInFile;
import cn.ohalo.stock.rule.StockRule;
import cn.ohalo.stock.rule.StockRuleDown;
import cn.ohalo.stock.rule.StockRuleUp;
import cn.ohalo.stock.service.StockInfoService;

/**
 * 股票买入推荐
 * 
 * @author halo
 * 
 */
public class StockRecommend {

	private StockOprInFile soif;

	public StockRecommend() {
		soif = StockOprInFile.getInstance();
	}

	/**
	 * 买入推荐
	 */
	public void buyRecommend() {
		List<CompanyStock> cstocks = soif.queryAllStock();
		final List<RegStockInfo> infos = new ArrayList<RegStockInfo>();
		for (Iterator<CompanyStock> iterator = cstocks.iterator(); iterator
				.hasNext();) {
			CompanyStock cs = iterator.next();
			RegStockInfo rsi = new RegStockInfo(cs.getStockCode(),
					"ss".equals(cs.getTeamOrg()) ? "sh" : "sz",
					(int) Math.round(10000 / cs.getCurrentPrice()),
					cs.getCurrentPrice());
			infos.add(rsi);
		}
		recommend(infos, new StockRuleDown(StockInfoService.getInstance()));
	}

	/**
	 * 买入推荐
	 */
	public void buyRecommend(StockRule srule) {
		List<CompanyStock> cstocks = soif.queryAllStock();
		final List<RegStockInfo> infos = new ArrayList<RegStockInfo>();
		for (Iterator<CompanyStock> iterator = cstocks.iterator(); iterator
				.hasNext();) {
			CompanyStock cs = iterator.next();
			RegStockInfo rsi = new RegStockInfo(cs.getStockCode(),
					"ss".equals(cs.getTeamOrg()) ? "sh" : "sz",
					(int) (100 / Math.round(cs.getCurrentPrice())) * 100,
					cs.getCurrentPrice());
			infos.add(rsi);
		}
		recommend(infos, srule);
	}

	public void batchSaleRecommend(StockRule srule) {
		soif.setAllStockfileAddress(ConfigBundle
				.getString("buy_stock_fileAddress"));
		List<StockInfo> infos = soif.queryAll(StockInfo.class);
		List<RegStockInfo> reginfos = new ArrayList<RegStockInfo>();
		for (Iterator<StockInfo> iterator = infos.iterator(); iterator
				.hasNext();) {
			StockInfo stockInfo = iterator.next();
			reginfos.add(stockToRegConvert(stockInfo));
		}
		saleRecommend(reginfos);
	}

	private RegStockInfo stockToRegConvert(StockInfo stockInfo) {
		if (stockInfo == null) {
			return new RegStockInfo();
		}
		String stockCode = stockInfo.getStockCode();
		String teamOrg = stockCode.substring(0, 2);
		String scode = stockCode.substring(2);
		Double price = stockInfo.getCurrentprice();
		int buynum = (int) Math.round(10000 / price);
		RegStockInfo info = new RegStockInfo(scode, teamOrg, buynum, price);
		return info;
	}

	/**
	 * 卖出提示
	 */
	public void saleRecommend(List<RegStockInfo> infos) {
		recommend(infos, new StockRuleUp(StockInfoService.getInstance()));
	}

	public void recommend(List<RegStockInfo> infos, StockRule srule) {
		LikeStock likeStock = new LikeStock();
		likeStock.setStockRule(srule);
		likeStock.profitWarn(infos);
	}
}
