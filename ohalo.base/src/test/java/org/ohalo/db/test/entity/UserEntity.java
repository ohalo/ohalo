package org.ohalo.db.test.entity;

import java.util.Map;

import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

public class UserEntity extends BaseEntity {

	/***
   * 
   */
	private static final long serialVersionUID = 3881954337312791522L;

	/**
	 * <pre>
	 * 方法体说明：(方法详细描述说明、方法参数的具体涵义)
	 * 作者：赵辉亮
	 * 日期：2013-8-22
	 * </pre>
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return "{userName:" + userName + ", userSex:" + userSex + ", age:"
				+ age + ", toString():" + super.toString() + "}";
	}

	private String userName;

	private String userSex;

	private String age;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public Map<String, Object> toMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBObject toDBObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJsonString() {
		return "{\"userName\":\"" + userName + "\", \"userSex\":" + userSex
				+ "\", \"age\":\"" + age + "\"," + super.toJsonString() + "}";
	}
}
