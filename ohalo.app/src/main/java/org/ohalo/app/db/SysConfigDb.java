package org.ohalo.app.db;

import org.ohalo.app.entity.SysConfigInfo;
import org.ohalo.db.impl.MongoHaloDBImpl;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class SysConfigDb extends MongoHaloDBImpl<SysConfigInfo> {

	@Override
	protected Class<SysConfigInfo> getEntityClass() {
		return SysConfigInfo.class;
	}

	public void update(SysConfigInfo value) {
		super.update(Query.query(Criteria.where("sid").is(value.getSid())),
				toUpdateMap(value.toMap()));
	}

	public void delete(String sid) {
		this.findAndRemove(Query.query(Criteria.where("sid").is(sid)));
	}
}
