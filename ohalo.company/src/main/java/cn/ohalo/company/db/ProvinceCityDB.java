package cn.ohalo.company.db;

import cn.ohalo.company.entity.ProvinceCityEntity;
import cn.ohalo.db.mongodb.BaseDb;

public class ProvinceCityDB extends BaseDb<ProvinceCityEntity> {

	private static ProvinceCityDB provinceCityDb;

	private ProvinceCityDB() {
		super();
	}

	public static ProvinceCityDB getInstance() {
		if (provinceCityDb != null) {
			provinceCityDb = new ProvinceCityDB();
		}
		return provinceCityDb;
	}
}