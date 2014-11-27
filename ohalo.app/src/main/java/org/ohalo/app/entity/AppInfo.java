package org.ohalo.app.entity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

@SuppressWarnings("rawtypes")
public class AppInfo extends BaseEntity implements Comparator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6450850965612690085L;

	/**
	 * 应用唯一标识
	 */
	private String appId;


	/**
	 * 应用类型
	 */
	private String appType;

	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 应用版本
	 * 
	 * @1.0.0.12344
	 * @1.0
	 * 
	 */
	private String appVersion;

	/**
	 * 版本类型 : 是bata版,还是正式版
	 */
	private String versionType;

	/**
	 * 应用编码
	 */
	private String appCode;

	/**
	 * 应用路径
	 */
	private String appPath;

	/**
	 * 应用描述
	 */
	private String appRemark;

	/**
	 * 应用分辨率大小
	 */
	private String appScreenSize;

	public AppInfo() {
		super();
	}

	public AppInfo(String appId, String appType, String appName,
			String appVersion, String appCode, String appPath,
			String appRemark, String appScreenSize, String versionType) {
		super();
		this.appId = appId;
		this.appType = appType;
		this.appName = appName;
		this.appVersion = appVersion;
		this.appCode = appCode;
		this.appPath = appPath;
		this.appRemark = appRemark;
		this.appScreenSize = appScreenSize;
		this.versionType = versionType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public String getAppRemark() {
		return appRemark;
	}

	public void setAppRemark(String appRemark) {
		this.appRemark = appRemark;
	}

	@Override
	public String toString() {
		return "AppInfo [appId=" + appId + ", appType=" + appType
				+ ", appName=" + appName + ", appVersion=" + appVersion
				+ ", appCode=" + appCode + ", appPath=" + appPath
				+ ", appRemark=" + appRemark + ",appScreenSize="
				+ appScreenSize + ",versionType=" + versionType + "]";
	}

	public Map<String, Object> toMap() {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("appId", appId);
		params.put("appType", appType);
		params.put("appName", appName);
		params.put("appVersion", appVersion);
		params.put("appCode", appCode);
		params.put("appPath", appPath);
		params.put("appRemark", appRemark);
		params.put("appScreenSize", appScreenSize);
		params.put("createTime", this.getCreateTime());
		params.put("versionType", versionType);

		return params;
	}

	public String getAppScreenSize() {
		return appScreenSize;
	}

	public void setAppScreenSize(String appScreenSize) {
		this.appScreenSize = appScreenSize;
	}

	/**
	 * 进行版本比较,
	 * 
	 * @版本号是1.1.1.2123123
	 * @版本号是1.0
	 * 
	 *          其中1.1肯定是比 前两位
	 */
	@Override
	public int compare(Object o1, Object o2) {
		AppInfo app1 = (AppInfo) o1;
		AppInfo app2 = (AppInfo) o2;

		return compareVersion(app1.getAppVersion(), app2.getAppVersion());
	}

	public int compareVersion(String appVersion1, String appVersion2) {
		if (StringUtils.isBlank(appVersion1)
				|| StringUtils.isBlank(appVersion2)) {
			return 0;
		}

		String[] version1 = appVersion1.split("\\.");
		String[] version2 = appVersion2.split("\\.");

		if (version2.length >= version1.length) {
			for (int i = 0; i < version1.length; i++) {
				if (!version1[i].equals(version2[i])) {
					Integer v1 = Integer.parseInt(version1[i]);
					Integer v2 = Integer.parseInt(version2[i]);
					return v1.compareTo(v2);
				}
			}

			return -1;
		} else {
			for (int i = 0; i < version2.length; i++) {
				if (!version1[i].equals(version2[i])) {
					Integer v1 = Integer.parseInt(version1[i]);
					Integer v2 = Integer.parseInt(version2[i]);
					return v1.compareTo(v2);
				}
			}
			return 1;
		}
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	@Override
	public DBObject toDBObject() {
		// TODO Auto-generated method stub
		return null;
	}

}