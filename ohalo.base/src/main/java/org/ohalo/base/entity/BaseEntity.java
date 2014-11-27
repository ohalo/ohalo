package org.ohalo.base.entity;

import java.io.Serializable;
import java.util.Map;

import com.mongodb.DBObject;

/**
 * 
 * @author halo
 * 
 */
public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8414228529663161008L;

	/**
	 * 唯一标识
	 */
	private String id;

	/**
	 * 创建用户名称
	 */
	private String createUser;

	/**
	 * 创建时间
	 */
	private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", createUser=" + createUser
				+ ", createTime=" + createTime + "]";
	}

	public String toJsonString() {
		return "'id':'" + id + "', 'createUser':'" + createUser
				+ "', 'createTime':'" + createTime + "'";
	}

	public abstract Map<String, Object> toMap();

	public abstract DBObject toDBObject();
}
