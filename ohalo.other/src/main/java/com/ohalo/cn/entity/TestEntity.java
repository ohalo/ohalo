package com.ohalo.cn.entity;

/**
 * Created with IntelliJ IDEA. User: Z.Halo Date: 13-9-26 Time: 下午1:39 To change
 * this template use File | Settings | File Templates.
 */
public class TestEntity {

	/**
	 * 姓名 *
	 */
	private String name;
	// 年龄
	private String age;
	/*
	 * 性别
	 */
	private String sex;

	public TestEntity() {
	}

	public TestEntity(String name, String age, String sex) {
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return this.name;
	}

	public String getAge() {
		return this.age;
	}

	public String getSex() {
		return this.sex;
	}

	@Override
	public String toString() {
		return "[TestEntity:{name:" + this.getName() + ",age:" + this.getAge()
				+ ",sex:" + this.getSex() + "}]";
	}
}