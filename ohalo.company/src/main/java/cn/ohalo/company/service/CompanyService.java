package cn.ohalo.company.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.ohalo.company.db.CompanyDB;
import cn.ohalo.company.entity.CompanyEntity;
import cn.ohalo.service.impl.BaseServiceImpl;
import cn.ohalo.utils.Guid;

import com.mongodb.BasicDBObject;

/**
 * 公司信息查询
 * 
 * @author halo
 * 
 */
public class CompanyService extends BaseServiceImpl<CompanyEntity> {

	private static CompanyService companyService;

	private CompanyService() {
		CompanyDB companyDb = CompanyDB.getInstance();
		companyDb.setCollectionName("cn.ohalo.company");
		setBaseDb(companyDb);
	}

	public static CompanyService getInstance() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}

	@Override
	public boolean insert(CompanyEntity t) {
		if (StringUtils.isBlank(t.getCompanyCode())) {
			t.setCompanyCode(Guid.newDCGuid("company"));
		}
		t.setCreateDate(new Date());
		return super.insert(t);
	}

	@Override
	public boolean saveOrUpdate(CompanyEntity t) {
		if (StringUtils.isNotBlank(t.getCompanyCode())) {
			return update(t);
		}
		return insert(t);
	}

	@Override
	public boolean update(CompanyEntity t) {
		return super.update(
				new BasicDBObject("companyCode", t.getCompanyCode()), t);
	}

}
