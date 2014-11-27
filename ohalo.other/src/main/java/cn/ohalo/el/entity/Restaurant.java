package cn.ohalo.el.entity;

import java.util.List;

import cn.ohalo.db.mongodb.MongoBaseEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 饭店
 * 
 * @author halo
 * 
 */
public class Restaurant extends MongoBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8556401848732683300L;

	private String name;

	private String address;

	private String id;

	private Long phoneNum;

	private String desc;

	private List<Food> foods;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(Long phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	@Override
	public String toString() {
		return "Restaurant [name=" + name + ", address=" + address + ", id="
				+ id + ", phoneNum=" + phoneNum + ", desc=" + desc + ", foods="
				+ foods + "]";
	}

	@Override
	public DBObject toDBObject() {
		DBObject obj = new BasicDBObject();
		obj.put("name", name);
		obj.put("address", address);
		obj.put("id", id);
		obj.put("phoneNum", phoneNum);
		obj.put("desc", desc);
		return obj;
	}

}
