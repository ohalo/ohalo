package cn.ohalo.stock.entity;

import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 维稳股票信息采集
 * 
 * @author halo
 * 
 */
public class StabilityStock {

	public String stockCode;

	public String stockName;

	public Map<Integer, Integer> section;

	public StabilityStock() {
		super();

	}

	public StabilityStock(String stockCode, String stockName,
			Map<Integer, Integer> section) {
		super();
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.section = section;
	}

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

	public Map<Integer, Integer> getSection() {
		return section;
	}

	public void setSection(Map<Integer, Integer> section) {
		this.section = section;
	}

	@Override
	public String toString() {
		return "StabilityStock [stockCode=" + stockCode + ", stockName="
				+ stockName + ", section=" + section + "]";
	}

	public DBObject toDBObject() {
		DBObject obj = new BasicDBObject();
		obj.put("stockCode", stockCode);
		obj.put("stockName", stockName);
		obj.put("section", section);
		return obj;
	}

}
