package cn.ohalo.interview.db;

import cn.ohalo.db.mongodb.BaseDb;
import cn.ohalo.interview.entity.InterViewInfo;

public class InterViewDB extends BaseDb<InterViewInfo> {

	private static InterViewDB db;

	private InterViewDB() {
		super();
	}

	public static InterViewDB getInstance() {
		if (db == null) {
			db = new InterViewDB();
		}
		return db;
	}
}