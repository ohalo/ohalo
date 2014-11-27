package cn.ohalo.stock.rule;

import java.util.Date;

import cn.ohalo.stock.entity.StockInfo;
import cn.ohalo.stock.notice.AlertMsg;
import cn.ohalo.stock.notice.MsgNotice;

public class StockRuleNormal extends StockRule {

	public StockRuleNormal() {
		setMsgNotice(AlertMsg.getInstance());
	}

	public StockRuleNormal(MsgNotice<String, StockInfo> msgNotice) {
		setMsgNotice(msgNotice);
	}

	@Override
	public void judgeStockTrend(String stockCode, Double currentPrice,
			Double profitStockPrice) {
		// TODO Auto-generated method stub
		String maxKey = "Max" + stockCode;
		String minKey = "Min" + stockCode;

		if (stockTrend.get(maxKey) == null
				|| stockTrend.get(maxKey) - 50 > profitStockPrice) {
			stockTrend.put(maxKey, profitStockPrice + 50);
		}

		if (stockTrend.get(minKey) == null
				|| stockTrend.get(minKey) + 50 < profitStockPrice) {
			stockTrend.put(minKey, profitStockPrice - 50);
		}

		Double maxPrice = stockTrend.get(maxKey);
		Double minPrice = stockTrend.get(minKey);

		if (logger.isInfoEnabled()) {
			logger.info("股票代码:" + stockCode + "最大盈利金额：" + maxPrice + ",最小盈利金额："
					+ minPrice);
		}

		StockInfo info = new StockInfo(stockCode, currentPrice, new Date(),
				profitStockPrice, "");

		if (profitStockPrice > maxPrice) {
			stockTrend.put(maxKey, profitStockPrice + 50);
		} else if (profitStockPrice < (maxPrice - 100)) {
			info.setType("卖出");
			stockPro.put(stockCode, info);
		}

		if (minPrice > profitStockPrice) {
			stockTrend.put(minKey, profitStockPrice - 50);
		} else if ((minPrice + 100) < profitStockPrice) {
			info.setType("买入");
			stockPro.put(stockCode, info);
		}

		if (profitStockPrice < -150) {
			stockPro.put(stockCode, info);
		}
	}

}
