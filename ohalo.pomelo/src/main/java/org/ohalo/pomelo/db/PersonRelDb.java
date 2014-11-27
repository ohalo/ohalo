package org.ohalo.pomelo.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ohalo.db.impl.MongoHaloDBImpl;
import org.ohalo.pomelo.entity.PersonRelInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 
 * @author halo
 * @since 2013年11月4日 上午11:32:31
 */
@SuppressWarnings("static-access")
public class PersonRelDb extends MongoHaloDBImpl<PersonRelInfo> {
	@Override
	protected Class<PersonRelInfo> getEntityClass() {
		return PersonRelInfo.class;
	}

	@Override
	public void update(PersonRelInfo personrel) {
		this.update(
				Query.query(Criteria.where("pida").is(personrel.getPida())
						.where("pidb").is(personrel.getPidb())),
				toUpdateMap(personrel.toMap()));
	}

	public List<PersonRelInfo> findByPida(String pida) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pida", pida);
		return this.queryByParams(params);
	}
	
	public List<PersonRelInfo> findByPidb(String pidb) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pidb", pidb);
		return this.queryByParams(params);
	}

	public void remove(PersonRelInfo relinfo) {
		this.findAndRemove(toQueryMap(relinfo.toMap()));
	}
}