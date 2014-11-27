package org.ohalo.pomelo.entity;

import java.util.HashMap;
import java.util.Map;

import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

/**
 * 人员信息
 * 
 * @author z.halo
 * @since 2013年10月14日 1.0
 */
public class PersonInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3117327884563967709L;

	/**
	 * 人员唯一标识
	 */
	private String pid;

	/**
	 * 人员名称
	 */
	private String pName;

	/**
	 * 人员年龄
	 */
	private String pAge;

	/**
	 * 人员性别
	 */
	private String pSex;

	/**
	 * 人员生日
	 */
	private String pBirthdate;

	/**
	 * 人员电话
	 */
	private String pPhone;

	/**
	 * 人员电子邮箱
	 */
	private String pEmail;

	/**
	 * 人员 住址
	 */
	private String pAddress;

	/**
	 * 绑定账户名称
	 */
	private String uName;

	public PersonInfo() {

	}

	public PersonInfo(String pid, String pName, String pAge, String pSex,
			String pBirthdate, String pPhone, String pEmail, String pAddress,
			String uName) {
		this.pid = pid;
		this.pName = pName;
		this.pAge = pAge;
		this.pSex = pSex;
		this.pBirthdate = pBirthdate;
		this.pPhone = pPhone;
		this.pEmail = pEmail;
		this.pAddress = pAddress;
		this.uName = uName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpAge() {
		return pAge;
	}

	public void setpAge(String pAge) {
		this.pAge = pAge;
	}

	public String getpSex() {
		return pSex;
	}

	public void setpSex(String pSex) {
		this.pSex = pSex;
	}

	public String getpBirthdate() {
		return pBirthdate;
	}

	public void setpBirthdate(String pBirthdate) {
		this.pBirthdate = pBirthdate;
	}

	public String getpPhone() {
		return pPhone;
	}

	public void setpPhone(String pPhone) {
		this.pPhone = pPhone;
	}

	public String getpEmail() {
		return pEmail;
	}

	public void setpEmail(String pEmail) {
		this.pEmail = pEmail;
	}

	public String getpAddress() {
		return pAddress;
	}

	public void setpAddress(String pAddress) {
		this.pAddress = pAddress;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PersonInfo [pid=" + pid + ", pName=" + pName + ", pAge=" + pAge
				+ ", pSex=" + pSex + ", pBirthdate=" + pBirthdate + ", pPhone="
				+ pPhone + ", pEmail=" + pEmail + ", pAddress=" + pAddress
				+ ", uName=" + uName + "]";
	}

	public Map<String, Object> toMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		params.put("pName", pName);
		params.put("pAge", pAge);
		params.put("pSex", pSex);
		params.put("pBirthdate", pBirthdate);
		params.put("pPhone", pPhone);
		params.put("pEmail", pEmail);
		params.put("pAddress", pAddress);
		params.put("uName", uName);
		return params;
	}

	@Override
	public DBObject toDBObject() {
		// TODO Auto-generated method stub
		return null;
	}
}