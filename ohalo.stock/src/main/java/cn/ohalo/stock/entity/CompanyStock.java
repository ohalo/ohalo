package cn.ohalo.stock.entity;

public class CompanyStock {

	private String stockCode;

	private String stockName;

	/**
	 * 当前价格
	 */
	private Double currentPrice;

	private String linkAddr;

	/**
	 * 所属机构 sh or sz
	 */
	private String teamOrg;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	@Override
	public String toString() {
		return "{'stockCode':'" + stockCode + "', 'stockName':'" + stockName
				+ "', 'linkAddr':'" + linkAddr + "','teamOrg':'" + teamOrg
				+ "','currentPrice':'" + (currentPrice == null ? 0.0
				: currentPrice) + "'}";
	}

	public String getTeamOrg() {
		return teamOrg;
	}

	public void setTeamOrg(String teamOrg) {
		this.teamOrg = teamOrg;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

}
