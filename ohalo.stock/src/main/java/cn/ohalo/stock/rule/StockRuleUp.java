package cn.ohalo.stock.rule;

import java.util.Date;

import cn.ohalo.stock.entity.StockInfo;
import cn.ohalo.stock.notice.AlertMsg;
import cn.ohalo.stock.notice.MsgNotice;

public class StockRuleUp extends StockRule {

	public StockRuleUp() {
		setMsgNotice(AlertMsg.getInstance());
	}

	public StockRuleUp(MsgNotice<String, StockInfo> msgNotice) {
		setMsgNotice(msgNotice);
	}

	@Override
	public void judgeStockTrend(String stockCode, Double currentPrice,
			Double profitStockPrice) {
		String maxKey = "Max" + stockCode;

		if (stockTrend.get(maxKey) == null) {
			stockTrend.put(maxKey, profitStockPrice + 50);
		}

		Double maxPrice = stockTrend.get(maxKey);

		if (logger.isInfoEnabled()) {
			logger.info("股票代码:" + stockCode + "最大盈利金额：" + maxPrice);
		}

		StockInfo info = new StockInfo(stockCode, currentPrice, new Date(),
				profitStockPrice, "卖出");

		if (profitStockPrice > maxPrice) {
			stockTrend.put(maxKey, profitStockPrice + 50);
		} else if (profitStockPrice < (maxPrice - 100)) {
			stockPro.put(stockCode, info);
		}

		if (profitStockPrice < -150) {
			stockPro.put(stockCode, info);
		}
	}

}
