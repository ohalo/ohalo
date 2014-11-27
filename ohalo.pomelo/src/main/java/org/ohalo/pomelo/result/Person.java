package org.ohalo.pomelo.result;

import java.io.Serializable;

import org.ohalo.pomelo.entity.PersonInfo;

/**
 * 人员返回结果信息
 * 
 * @author z.halo
 * @since 2013年10月14日 1.0
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6835880939622266833L;

	/**
	 * 人员关联关系
	 */
	private String personRelType;

	private PersonInfo person;

	public String getPersonRelType() {
		return personRelType;
	}

	public void setPersonRelType(String personRelType) {
		this.personRelType = personRelType;
	}

	public PersonInfo getPerson() {
		return person;
	}

	public void setPerson(PersonInfo person) {
		this.person = person;
	}

}
