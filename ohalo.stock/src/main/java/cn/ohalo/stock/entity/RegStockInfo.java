package cn.ohalo.stock.entity;

import cn.ohalo.stock.cache.entity.BaseEntity;

/**
 * 注册股票信息
 * 
 * @author halo
 * 
 */
public class RegStockInfo extends BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8715865247385723892L;

	/**
	 * 股票代码
	 */
	private String stockCode;

	/**
	 * 股票属性：sh 上海证券所，sz 深圳证券所
	 */
	private String teamOrg = "sh";

	/**
	 * 买入股票数量
	 */
	private int buyNum;

	/**
	 * 买入股票价格
	 */
	private Double buyPrice;

	public String getStockCode() {
		return stockCode;
	}

	public String getToStockCode() {
		return teamOrg + stockCode;
	}

	public RegStockInfo() {
	}

	public RegStockInfo(String stockCode, String teamOrg, int buyNum,
			Double buyPrice) {
		super();
		this.stockCode = stockCode;
		this.teamOrg = teamOrg;
		this.buyNum = buyNum;
		this.buyPrice = buyPrice;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getTeamOrg() {
		return teamOrg;
	}

	public void setTeamOrg(String teamOrg) {
		this.teamOrg = teamOrg;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	@Override
	public String toString() {
		return "RegStockInfo [stockCode=" + stockCode + ", teamOrg=" + teamOrg
				+ ", buyNum=" + buyNum + ", buyPrice=" + buyPrice + "]";
	}

	@Override
	public String getId() {
		return this.getStockCode();
	}

}
