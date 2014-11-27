package cn.ohalo.company.db;

import cn.ohalo.company.entity.CompanyEntity;
import cn.ohalo.db.mongodb.BaseDb;

public class CompanyDB extends BaseDb<CompanyEntity> {

	private static CompanyDB companyDb;

	public static CompanyDB getInstance() {
		if (companyDb == null) {
			companyDb = new CompanyDB();
		}

		return companyDb;
	}
}
