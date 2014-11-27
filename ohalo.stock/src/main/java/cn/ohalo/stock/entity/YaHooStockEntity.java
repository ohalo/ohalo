package cn.ohalo.stock.entity;

import java.io.Serializable;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class YaHooStockEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6916655560989258911L;

	// 记录时间
	private Date recordDate;

	// 开盘金额
	private Double openPrice;

	// 最高金额
	private Double highPrice;

	// 最低金额
	private Double lowPrice;

	// 收盘金额
	private Double closePrice;

	// 成交金额
	private Double volumePrice;

	// adj Close
	private Double adjClosePrice;

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	public Double getVolumePrice() {
		return volumePrice;
	}

	public void setVolumePrice(Double volumePrice) {
		this.volumePrice = volumePrice;
	}

	public Double getAdjClosePrice() {
		return adjClosePrice;
	}

	public void setAdjClosePrice(Double adjClosePrice) {
		this.adjClosePrice = adjClosePrice;
	}

	@Override
	public String toString() {
		return "YaHooStockEntity [recordDate=" + recordDate + ", openPrice="
				+ openPrice + ", highPrice=" + highPrice + ", lowPrice="
				+ lowPrice + ", closePrice=" + closePrice + ", volumePrice="
				+ volumePrice + ", adjClosePrice=" + adjClosePrice + "]";
	}

	public YaHooStockEntity(Date recordDate, Double openPrice,
			Double highPrice, Double lowPrice, Double closePrice,
			Double volumePrice, Double adjClosePrice) {
		super();
		this.recordDate = recordDate;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.volumePrice = volumePrice;
		this.adjClosePrice = adjClosePrice;
	}

	public DBObject toDBObject() {
		DBObject objStock = new BasicDBObject();
		if (recordDate != null) {
			objStock.put("recordDate", recordDate);
		}
		if (openPrice != null && openPrice > 0) {
			objStock.put("openPrice", openPrice);
		}
		if (highPrice != null && highPrice > 0) {
			objStock.put("highPrice", highPrice);
		}
		if (lowPrice != null && lowPrice > 0) {
			objStock.put("lowPrice", lowPrice);
		}
		if (closePrice != null && closePrice > 0) {
			objStock.put("closePrice", closePrice);
		}
		if (volumePrice != null && volumePrice > 0) {
			objStock.put("volumePrice", volumePrice);
		}
		if (adjClosePrice != null && adjClosePrice > 0) {
			objStock.put("adjClosePrice", adjClosePrice);
		}
		return objStock;
	}

	public YaHooStockEntity() {
		super();
	}
}
