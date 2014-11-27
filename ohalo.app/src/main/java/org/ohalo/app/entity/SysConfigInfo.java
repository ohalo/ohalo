package org.ohalo.app.entity;

import java.util.HashMap;
import java.util.Map;

import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

/**
 * 系统配置信息
 * 
 * @author halo
 * @since 2013年11月13日 下午5:50:59
 */
public class SysConfigInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2338139648377092226L;

	/**
	 * 系统配置信息id
	 */
	private String sid;

	/**
	 * 系统配置编码
	 */
	private String sysConfigCode;

	/**
	 * 系统配置名称
	 */
	private String sysConfigName;

	/**
	 * 系统配置值
	 */
	private String sysConfigValue;

	public SysConfigInfo() {
		super();
	}

	public SysConfigInfo(String sid, String sysConfigCode,
			String sysConfigName, String sysConfigValue, String createTime) {
		super();
		this.sid = sid;
		this.sysConfigCode = sysConfigCode;
		this.sysConfigName = sysConfigName;
		this.sysConfigValue = sysConfigValue;
		this.setCreateTime(createTime);
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSysConfigCode() {
		return sysConfigCode;
	}

	public void setSysConfigCode(String sysConfigCode) {
		this.sysConfigCode = sysConfigCode;
	}

	public String getSysConfigName() {
		return sysConfigName;
	}

	public void setSysConfigName(String sysConfigName) {
		this.sysConfigName = sysConfigName;
	}

	public String getSysConfigValue() {
		return sysConfigValue;
	}

	public void setSysConfigValue(String sysConfigValue) {
		this.sysConfigValue = sysConfigValue;
	}

	@Override
	public String toString() {
		return "SysConfigInfo [sid=" + sid + ", sysConfigCode=" + sysConfigCode
				+ ", sysConfigName=" + sysConfigName + ", sysConfigValue="
				+ sysConfigValue + "]";
	}

	public Map<String, Object> toMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sid", sid);
		params.put("sysConfigCode", sysConfigCode);
		params.put("sysConfigName", sysConfigName);
		params.put("sysConfigValue", sysConfigValue);
		params.put("createTime", getCreateTime());
		return params;
	}

	@Override
	public DBObject toDBObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
