package cn.ohalo.el.entity;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import cn.ohalo.db.mongodb.MongoBaseEntity;

/**
 * 食物
 * 
 * @author halo
 * 
 */
public class Food extends MongoBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2795949013991535478L;

	private String id;

	private String name;

	private double price;

	private String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public DBObject toDBObject() {
		DBObject obj = new BasicDBObject();
		if (StringUtils.isNotBlank(id)) {
			obj.put("id", id);
		}
		if (StringUtils.isNotBlank(name)) {
			obj.put("name", name);
		}
		if (price > 0.0) {
			obj.put("price", price);
		}
		if (StringUtils.isNotBlank(desc)) {
			obj.put("desc", desc);
		}
		return obj;
	}
}
