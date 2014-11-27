package cn.ohalo.company.service;

import cn.ohalo.company.entity.CompanyEntity;
import junit.framework.TestCase;

public class CompanyServiceTest extends TestCase {

	CompanyService companySerivce;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		companySerivce = CompanyService.getInstance();
	}

	public void testInsert() {

		for (int i = 0; i < 100; i++) {
			CompanyEntity entity = new CompanyEntity();
			entity.setCompanyAddress("");
			entity.setCompanyName("公司" + i);
			entity.setUrlAddress("http://ohalo.cn");
			companySerivce.insert(entity);
		}
	}

}
