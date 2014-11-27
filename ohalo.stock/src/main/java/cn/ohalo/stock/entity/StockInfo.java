package cn.ohalo.stock.entity;

import java.util.Date;

import cn.ohalo.stock.cache.entity.BaseEntity;

public class StockInfo extends BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6497450308030484369L;

	public static String BUY_IN_TYPE = "buyIn";
	public static String BUY_OUT_TYPE = "buyOut";

	String stockCode;

	/**
	 * 当前价格
	 */
	Double currentprice;

	/**
	 * 当前时间
	 */
	Date currentDate;

	/**
	 * 盈利金额
	 */
	Double profitStockPrice;

	/**
	 * buyIn，buyOut
	 */
	String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public StockInfo(String stockCode, Double currentprice, Date currentDate,
			Double profitStockPrice, String type) {
		super();
		this.stockCode = stockCode;
		this.currentprice = currentprice;
		this.currentDate = currentDate;
		this.profitStockPrice = profitStockPrice;
		this.type = type;
	}

	public Double getProfitStockPrice() {
		return profitStockPrice;
	}

	public void setProfitStockPrice(Double profitStockPrice) {
		this.profitStockPrice = profitStockPrice;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Double getCurrentprice() {
		return currentprice;
	}

	public void setCurrentprice(Double currentprice) {
		this.currentprice = currentprice;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	@Override
	public String toString() {
		return "{'stockCode'：'" + stockCode + "', 'currentprice'：'"
				+ currentprice + "','profitStockPrice':'" + profitStockPrice
				+ "','currentDate':'" + currentDate + "','type'：'" + type
				+ "']";
	}

	public String toPrintString() {
		return "购买股票 [股票代码：" + stockCode + ", 股票当前价格：" + currentprice
				+ ",盈利金額:" + profitStockPrice + ", 购买日期:" + currentDate
				+ ",购买标识：" + type + "]";
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return stockCode;
	}

}
