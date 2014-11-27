package org.ohalo.pomelo.entity;

import java.util.HashMap;
import java.util.Map;

import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

/**
 * 用户信息
 * 
 * @author z.halo
 * @since 2013年10月14日 1.0
 */
public class UserInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5199168192200062480L;

	/**
	 * 账户名
	 */
	private String uAccount;

	/**
	 * 账户密码
	 */
	private String uPassword;

	/**
	 * 账户唯一标识
	 */
	private String uid;

	/**
	 * 账户状态
	 */
	private String uStatus;

	/**
	 * 柚子类型
	 */
	private String uziType;

	public UserInfo() {

	}

	public UserInfo(String uAccount, String uPassword, String uid,
			String uStatus, String uziType) {
		this.uAccount = uAccount;
		this.uPassword = uPassword;
		this.uid = uid;
		this.uStatus = uStatus;
		this.uziType = uziType;
	}

	public String getuAccount() {
		return uAccount;
	}

	public void setuAccount(String uAccount) {
		this.uAccount = uAccount;
	}

	public String getuPassword() {
		return uPassword;
	}

	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getuStatus() {
		return uStatus;
	}

	public void setuStatus(String uStatus) {
		this.uStatus = uStatus;
	}

	@Override
	public String toString() {
		return "UserInfo [uAccount=" + uAccount + ", uPassword=" + uPassword
				+ ", uid=" + uid + ", uStatus=" + uStatus + ",uziType="
				+ uziType + ", getCreateTime()=" + getCreateTime() + "]";
	}

	public Map<String, Object> toMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uAccount", uAccount);
		params.put("uPassword", uPassword);
		params.put("uid", uid);
		params.put("uStatus", uStatus);
		params.put("uziType", uziType);
		params.put("createTime", getCreateTime());
		return params;
	}

	public String getUziType() {
		return uziType;
	}

	public void setUziType(String uziType) {
		this.uziType = uziType;
	}

	@Override
	public DBObject toDBObject() {
		// TODO Auto-generated method stub
		return null;
	}
}