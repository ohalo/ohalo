
package org.ohalo.pomelo.db;

import java.util.HashMap;
import java.util.Map;

import org.ohalo.db.impl.MongoHaloDBImpl;
import org.ohalo.pomelo.entity.PersonInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 
 * @author halo
 * @since 2013年11月4日 上午11:32:22
 */
public class PersonDb extends MongoHaloDBImpl<PersonInfo> {
	@Override
	protected Class<PersonInfo> getEntityClass() {
		return PersonInfo.class;
	}

	@Override
	public void update(PersonInfo person) {
		this.update(Query.query(Criteria.where("pid").is(person.getPid())),
				toUpdateMap(person.toMap()));
	}

	/**
	 * 通过ID获取记录
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public PersonInfo findById(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", id);
		return this.findOne(toQueryMap(params));
	}

	/**
	 * 通过ID获取记录
	 * 
	 * @param id
	 * @return
	 */
	public PersonInfo findByuAccount(String uAccount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uName", uAccount);
		return this.findOne(toQueryMap(params));
	}

	/**
	 * 通过电话号码获取人员信息
	 * 
	 * @param pPhone
	 * @return
	 */
	public PersonInfo findBypPhone(String pPhone) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pPhone", pPhone);
		return this.findOne(toQueryMap(params));
	}
}