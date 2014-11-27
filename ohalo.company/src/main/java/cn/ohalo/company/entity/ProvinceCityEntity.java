package cn.ohalo.company.entity;

import org.apache.commons.lang.StringUtils;

import cn.ohalo.db.mongodb.MongoBaseEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 省份城市信息
 * 
 * @author halo
 * 
 */
public class ProvinceCityEntity extends MongoBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8898198259395108230L;

	/**
	 * 地区编码
	 */
	private String code;

	/**
	 * 地区名称
	 */
	private String name;

	/**
	 * 地市所属的行政级别
	 * 
	 * 省，市，县，区，乡（镇），村，组
	 */
	private String unit;

	/**
	 * 所属父地区信息
	 */
	private ProvinceCityEntity parentEntity;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProvinceCityEntity getParentEntity() {
		return parentEntity;
	}

	public void setParentEntity(ProvinceCityEntity parentEntity) {
		this.parentEntity = parentEntity;
	}

	@Override
	public String toString() {
		return "ProvinceCityEntity [code=" + code + ", name=" + name
				+ ", unit=" + unit + ", parentEntity="
				+ parentEntity.toString() + "]";
	}

	@Override
	public DBObject toDBObject() {
		DBObject provCity = new BasicDBObject();
		if (StringUtils.isNotBlank(code)) {
			provCity.put("code", code);
		}

		if (StringUtils.isNotBlank(unit)) {
			provCity.put("unit", unit);
		}

		if (StringUtils.isNotBlank(name)) {
			provCity.put("name", name);
		}

		if (parentEntity != null) {
			provCity.put("parentEntity", parentEntity.toDBObject());
		}
		return provCity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
