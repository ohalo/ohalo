package org.ohalo.pomelo;

import org.ohalo.pomelo.entity.PersonInfo;
import org.ohalo.pomelo.result.Person;

import junit.framework.TestCase;

public class PersonInfoTest extends TestCase {

	public void testPersonInfo() {
		PersonInfo info = new PersonInfo();
		info.setCreateTime("20120305");
		info.setpAddress("天堂");
		Person person = new Person();
		person.setPerson(info);
		System.out.println(person.getPerson().getpAddress());
	}
}
