package org.ohalo.db.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.ohalo.db.HaloDBException;
import org.ohalo.db.UserManager;
import org.ohalo.db.test.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 
 * @author halo
 * 
 */
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class MongoDBTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	public UserManager halodb;

	@Test
	public void testFind() throws HaloDBException {
		UserEntity entity = new UserEntity();
		entity.setAge("12");
		entity.setId("3");
		entity.setUserName("lily");
		entity.setUserSex("male");
		halodb.save(entity);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", "3");
		System.out.println(halodb.queryByParams(paramsMap).toString());
	//	System.out.println(halodb.findById("1").toString());
	}

	@Test
	public void testMongoJsonParse() {
		UserEntity entity = new UserEntity();
		entity.setAge("12");
		entity.setId("3");
		entity.setUserName("lily");
		entity.setUserSex("male");
		System.out.println(entity.toJsonString());

	}
}
