package cn.ohalo.stock.rule;

import java.util.Date;

import cn.ohalo.stock.entity.StockInfo;
import cn.ohalo.stock.notice.AlertMsg;
import cn.ohalo.stock.notice.MsgNotice;

public class StockRuleDown extends StockRule {

	public StockRuleDown() {
		setMsgNotice(AlertMsg.getInstance());
	}

	public StockRuleDown(MsgNotice<String, StockInfo> msgNotice) {
		setMsgNotice(msgNotice);
	}

	@Override
	public void judgeStockTrend(String stockCode, Double currentPrice,
			Double profitStockPrice) {
		// TODO Auto-generated method stub
		String minKey = "Min" + stockCode;

		Double minPrice = stockTrend.get(minKey);

		if (minPrice == null) {
			stockTrend.put(minKey, profitStockPrice - 50);
		}

		minPrice = stockTrend.get(minKey);

		if (logger.isInfoEnabled()) {
			logger.info("股票代码:" + stockCode + ",最大亏损金额：" + minPrice
					+ ",当前盈利金额：" + profitStockPrice);
		}

		StockInfo info = new StockInfo(stockCode, currentPrice, new Date(),
				profitStockPrice, "买入");

		if (minPrice > profitStockPrice) {
			stockTrend.put(minKey, profitStockPrice - 50);
		} else if ((minPrice + 100) < profitStockPrice
				&& profitStockPrice < (minPrice + 150)) {
			stockPro.put(stockCode, info);
		}
	}

}
