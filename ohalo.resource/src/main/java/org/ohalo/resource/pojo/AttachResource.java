package org.ohalo.resource.pojo;

import java.util.HashMap;
import java.util.Map;

import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

/**
 * 
 * 
 * <pre>
 * 功能：AttachResource 附件资源实体类，
 * 里面包含一些参数信息
 * 如附件关联id
 * 附件名称
 * 附件大小
 * 附件的路径，
 * 附加的类型等等一些扩展字段
 * 作者：赵辉亮
 * 日期：2013-6-25下午6:03:17
 * </pre>
 */
public class AttachResource extends BaseEntity {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = -8029186543648898355L;

	// 附件id
	private String fid;

	// 附件关联外键id
	private String attachguid;

	// 附件的名称
	private String attachname;

	// 附件的类型
	private String attachtype;

	// 附件的路径
	private String attachpath;

	// 附件的大小
	private String attachsize;

	// 扩展字段1
	private String extra1;

	// 扩展字段2
	private String extra2;

	// 扩展字段3
	private String extra3;

	// 无参的构造函数
	public AttachResource() {
		super();
	}

	/**
	 * 有餐的构造函数，其中包括附件关联id，附件名称，附件类型 附件的路径和附件的大小
	 * 
	 * @param attachguid
	 * @param attachname
	 * @param attachtype
	 * @param attachpath
	 * @param attachsize
	 */
	public AttachResource(String attachguid, String attachname,
			String attachtype, String attachpath, String attachsize) {
		super();
		this.attachguid = attachguid;
		this.attachname = attachname;
		this.attachtype = attachtype;
		this.attachpath = attachpath;
		this.attachsize = attachsize;
	}

	/***
	 * @return the attachguid
	 */
	public String getAttachguid() {
		return attachguid;
	}

	/***
	 * @param attachguid
	 *            the attachguid to set
	 */
	public void setAttachguid(String attachguid) {
		this.attachguid = attachguid;
	}

	/***
	 * @return the attachname
	 */
	public String getAttachname() {
		return attachname;
	}

	/***
	 * @param attachname
	 *            the attachname to set
	 */
	public void setAttachname(String attachname) {
		this.attachname = attachname;
	}

	/***
	 * @return the attachtype
	 */
	public String getAttachtype() {
		return attachtype;
	}

	/***
	 * @param attachtype
	 *            the attachtype to set
	 */
	public void setAttachtype(String attachtype) {
		this.attachtype = attachtype;
	}

	/***
	 * @return the attachpath
	 */
	public String getAttachpath() {
		return attachpath;
	}

	/***
	 * @param attachpath
	 *            the attachpath to set
	 */
	public void setAttachpath(String attachpath) {
		this.attachpath = attachpath;
	}

	/***
	 * @return the attachsize
	 */
	public String getAttachsize() {
		return attachsize;
	}

	/***
	 * @param attachsize
	 *            the attachsize to set
	 */
	public void setAttachsize(String attachsize) {
		this.attachsize = attachsize;
	}

	/***
	 * @return the extra1
	 */
	public String getExtra1() {
		return extra1;
	}

	/***
	 * @param extra1
	 *            the extra1 to set
	 */
	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	/***
	 * @return the extra2
	 */
	public String getExtra2() {
		return extra2;
	}

	/***
	 * @param extra2
	 *            the extra2 to set
	 */
	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	/***
	 * @return the extra3
	 */
	public String getExtra3() {
		return extra3;
	}

	/***
	 * @param extra3
	 *            the extra3 to set
	 */
	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}
	
	@Override
	public String toString() {
		return "AttachResource [fid=" + fid + ", attachguid=" + attachguid
				+ ", attachname=" + attachname + ", attachtype=" + attachtype
				+ ", attachpath=" + attachpath + ", attachsize=" + attachsize
				+ ", extra1=" + extra1 + ", extra2=" + extra2 + ", extra3="
				+ extra3 + "]";
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fid", fid);
		params.put("attachguid", attachguid);
		params.put("attachname", attachname);
		params.put("attachtype", attachtype);
		params.put("attachpath", attachpath);
		params.put("attachsize", attachsize);
		params.put("extra1", extra1);
		params.put("extra2", extra2);
		params.put("extra3", extra3);
		params.put("createTime", getCreateTime());
		return params;
	}

	@Override
	public DBObject toDBObject() {
		// TODO Auto-generated method stub
		return null;
	}

}