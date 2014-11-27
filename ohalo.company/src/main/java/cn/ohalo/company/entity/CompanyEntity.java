package cn.ohalo.company.entity;

import org.apache.commons.lang.StringUtils;

import cn.ohalo.db.mongodb.MongoBaseEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 公司实体类，记录公司信息，包括公司名称，公司地址，公司url，公司描述信息
 * 
 * @author z.halo
 * @since 2013年10月9日 1.0
 */
public class CompanyEntity extends MongoBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3310136044411776126L;

	/**
	 * 所属省市信息
	 */
	private ProvinceCityEntity provCity;

	/**
	 * 公司编码
	 */
	private String companyCode;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 公司地址
	 */
	private String companyAddress;

	/**
	 * 链接地址
	 */
	private String urlAddress;

	/**
	 * 公司描述
	 */
	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 返回公司名称，如：上海德邦物流有限公司
	 * 
	 * @return
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 
	 * @param companyName
	 *            公司名称
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 返回公司地址，如：上海市青浦区明珠路1018号
	 * 
	 * @return
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}

	/**
	 * 
	 * @param companyAddress
	 *            公司地址
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	/**
	 * 获取公司链接地址，如：http://www.deppon.com
	 * 
	 * @return
	 */
	public String getUrlAddress() {
		return urlAddress;
	}

	/**
	 * 
	 * @param urlAddress
	 *            公司访问地址
	 */
	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	public ProvinceCityEntity getProvCity() {
		return provCity;
	}

	public void setProvCity(ProvinceCityEntity provCity) {
		this.provCity = provCity;
	}

	@Override
	public DBObject toDBObject() {

		DBObject db = new BasicDBObject();

		if (provCity != null) {
			db.put("provCity", provCity.toDBObject());
		}
		if (StringUtils.isNotBlank(companyCode)) {
			db.put("companyCode", companyCode);
		}
		if (StringUtils.isNotBlank(companyName)) {
			db.put("companyName", companyName);
		}
		if (StringUtils.isNotBlank(companyAddress)) {
			db.put("companyAddress", companyAddress);
		}
		if (StringUtils.isNotBlank(urlAddress)) {
			db.put("urlAddress", urlAddress);
		}
		if (StringUtils.isNotBlank(desc)) {
			db.put("desc", desc);
		}
		return db;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	public String toString() {
		return "CompanyEntity [provCity=" + provCity + ", companyCode="
				+ companyCode + ", companyName=" + companyName
				+ ", companyAddress=" + companyAddress + ", urlAddress="
				+ urlAddress + ", desc=" + desc + "]";
	}
}
