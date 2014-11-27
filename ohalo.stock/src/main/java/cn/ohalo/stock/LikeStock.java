package cn.ohalo.stock;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ohalo.stock.entity.RegStockInfo;
import cn.ohalo.stock.rule.StockRule;
import cn.ohalo.stock.socket.SinaStockClient;

/**
 * 首先分析数据量大小，
 * 
 * 求最大值和最小值 （股票的开盘的值）
 * 
 * 1.然后对这些值进行分批处理： 如： 0<=x<1 1<=x<2 等等，进行分块计算处理。做数据统计求出在这个区间段的金额数
 * 
 * 2.根据区间段，排除最大最小序列分布，找稳定投资区间。
 * 
 * 3.对资金数据区间，做区间分类 如：买50股 在那个时间进那个时间出可以赚1元 买100股在那个时间进那个时间出可以赚10元，如此每天计算参数
 * 
 * 
 * 
 * @author halo
 * 
 * 
 *         做之前，需要进行数据库选型
 */
public class LikeStock {

	public static Log logger = LogFactory.getLog(LikeStock.class);

	private SinaStockClient client = SinaStockClient.getInstance();

	private StockRule stockRule;

	public void setStockRule(StockRule stockRule) {
		this.stockRule = stockRule;
	}

	/**
	 * 计算单个股票，买入价格和卖出价格，赚区的差价
	 * 
	 * @param stockCode
	 *            购买股票代码
	 * @param buyNum
	 *            买入数量
	 * @param buyPrice
	 *            买入价格
	 * @param profitPrice
	 *            盈利多少
	 * @return
	 */
	public Double calcStockProfit(String stockCode, int buyNum,
			Double buyPrice, Double profitPrice) {
		if (logger.isDebugEnabled()) {
			logger.debug("购买股票代码：" + stockCode + ",买入价格:" + buyPrice
					+ "(元),买入数量：" + buyNum + "(股),盈利多少元:" + profitPrice + "(元)");
		}
		return (buyPrice * buyNum + profitPrice + (19.9 + 39.9)) / buyNum;
	}

	/**
	 * 计算盈利金额
	 * 
	 * @param stockCode
	 * @param buyPrice
	 * @param buyNum
	 * @param currentPrice
	 * @return
	 */
	public Double calcProfitPrice(String stockCode, Double buyPrice,
			int buyNum, Double currentPrice) {
		if (logger.isDebugEnabled()) {
			logger.debug("购买股票代码：" + stockCode + ",买入价格:" + buyPrice
					+ "(元),买入数量：" + buyNum + "(股),当前多少元:" + currentPrice
					+ "(元)");
		}
		return currentPrice * buyNum - buyPrice * buyNum - (19.9 + 39.9);
	}

	/**
	 * 注册股票代码
	 * 
	 * @param stockCodes
	 */
	public void regiterStockCodes(List<String> stockCodes) {
		client.setStockCodes(stockCodes);
	}

	/**
	 * 选取股票应选择股票在平稳区间之内的股票，（如在区间之外，需要进行风险评估）
	 * 
	 * 拿一个股票最近五天的股票信息，求股票的最高点平均数和最低点平均数，然后那最高点-2，最低点+2，最低买入报警范围和最高抛售报警范围，
	 * 
	 * 然后在这个范围内，计算收益总额，0,50,100（保本价，赚50元，赚100元）
	 * 
	 * 设盈利参数为x
	 * 
	 * x> 0都属于在盈利范围之内
	 * 
	 * 是否盈利，计算范围 x = 0 ，0< x <=50,50<x<=100,100<=x
	 */
	public void calcStockIsProfit(String stockCode, int buyNum, Double buyPrice) {

		// 无盈利
		Double profitStockPricez = calcStockProfit(stockCode, buyNum, buyPrice,
				0.0);
		// 盈利50
		Double profitStockPricef = calcStockProfit(stockCode, buyNum, buyPrice,
				50.0);
		// 盈利100
		Double profitStockPriceo = calcStockProfit(stockCode, buyNum, buyPrice,
				100.0);

		Double currentStockPrice = client.getCurrentStockPricebyCode(stockCode);

		if (currentStockPrice == null || currentStockPrice == 0.0) {
			return;
		}

		currentStockPrice = currentStockPrice == null ? 0.0 : currentStockPrice;

		if (logger.isDebugEnabled()) {
			logger.debug("无盈利价格：" + profitStockPricez + ",盈利50元价格："
					+ profitStockPricef + "盈利100元价格：" + profitStockPriceo
					+ "购买股票代码：" + stockCode + ",购买数量：" + buyNum + ",购买金额："
					+ buyPrice + "(元),当前金额：" + currentStockPrice + "(元)");
		}

		String str = "你的股票%s,购买股票代码："
				+ stockCode
				+ ",购买数量："
				+ buyNum
				+ ",购买金额："
				+ buyPrice
				+ "(元),当前金额："
				+ currentStockPrice
				+ "(元)，计算盈利金额："
				+ calcProfitPrice(stockCode, buyPrice, buyNum,
						currentStockPrice);

		stockRule
				.judgeStockTrend(
						stockCode,
						currentStockPrice,
						calcProfitPrice(stockCode, buyPrice, buyNum,
								currentStockPrice));

		if (currentStockPrice <= profitStockPricez) {
			str = String.format(str, "已亏本");
			// stockPro.put(stockCode, str);
		}

		if (currentStockPrice > profitStockPricez
				&& currentStockPrice <= profitStockPricef) {
			str = String.format(str, "已盈利,盈利区间在0-50元之间");
			// stockPro.put(stockCode, str);
		}

		if (currentStockPrice > profitStockPricef
				&& currentStockPrice <= profitStockPriceo) {
			str = String.format(str, "已盈利,盈利区间在50-100元之间");
			// stockPro.put(stockCode, str);
		}

		if (currentStockPrice > profitStockPriceo) {
			str = String.format(str, "已盈利,盈利大于100元");
			// stockPro.put(stockCode, str);
		}
		if (logger.isInfoEnabled()) {
			logger.info(str);
		}
	}

	/**
	 * 股票盈亏信息警告
	 * 
	 * @param infos
	 */
	public void profitWarn(final List<RegStockInfo> infos) {

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				List<String> a = new ArrayList<String>();
				for (RegStockInfo regStockInfo : infos) {
					if (StringUtils.isBlank(regStockInfo.getToStockCode())) {
						continue;
					}
					a.add(regStockInfo.getToStockCode());
				}
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					regiterStockCodes(a);
				}

			}
		});

		thread1.start();

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					for (RegStockInfo regStockInfo : infos) {
						if (StringUtils.isBlank(regStockInfo.getToStockCode())) {
							continue;
						}
						calcStockIsProfit(regStockInfo.getToStockCode(),
								regStockInfo.getBuyNum(),
								regStockInfo.getBuyPrice());
					}
				}

			}
		});
		thread2.start();
	}
}
