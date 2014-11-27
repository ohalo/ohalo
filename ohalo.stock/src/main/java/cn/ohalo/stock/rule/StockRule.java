package cn.ohalo.stock.rule;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ohalo.stock.entity.StockInfo;
import cn.ohalo.stock.notice.MsgNotice;

/**
 * 股票规则
 * 
 * @author halo
 * 
 */
public abstract class StockRule {

	static Map<String, Double> stockTrend = new HashMap<String, Double>();

	static Log logger = LogFactory.getLog(StockRule.class);

	MsgNotice<String, StockInfo> stockPro;

	public void setMsgNotice(MsgNotice<String, StockInfo> msgNotice) {
		this.stockPro = msgNotice;
	}

	/**
	 * <pre>
	 * 每一只股票在很短的时间之内都有相应的走势，有趋向高位，有走势低位，但是大致有一个走势图
	 * 鄙人根据股票走势，略施小计，按其趋势设其规则，凡股票出其规则意为出具，在其规则意为入局，大凡股票
	 * 3000余只，出具入局者甚多，大升大降为我所不容。
	 * 
	 * 现告其规则有甚：一只股票入局为0，涨势大凡有几个阶段0-50，50-100,100-150,150-200,200-250，以五十为基，
	 * 欲往上越是红股 以50为基，-50-0，-50 -- -100 越往下越是蓝股，上下执事可算防火执事，可保股票安定,赚钱有道
	 * 
	 * 首先记录股票的最高值，如盈利215，在区间200-250之间，根据趋势判断是否抛售，如股票下跌，盈利175，在150-200
	 * 之间是为中间观摩阶段不做动作 如股票继续下跌，盈利120，在100-150区间，则视为股票以跨越警戒线，是为抛售，不可再做观察。
	 * 如股票一直涨，则不去理会
	 * 
	 * 如果股票一直跌，则按最小值-150元为基线抛出
	 * </pre>
	 * 
	 * @param stockCode
	 *            当前股票代码
	 * @param profitStockPrice
	 *            盈利价格
	 */
	public abstract void judgeStockTrend(String stockCode, Double currentPrice,
			Double profitStockPrice);
}
