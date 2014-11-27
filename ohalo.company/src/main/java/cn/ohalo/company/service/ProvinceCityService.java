package cn.ohalo.company.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.ohalo.company.db.ProvinceCityDB;
import cn.ohalo.company.entity.ProvinceCityEntity;
import cn.ohalo.service.impl.BaseServiceImpl;
import cn.ohalo.utils.Guid;

import com.mongodb.BasicDBObject;

/**
 * 省会城市信息
 * 
 * @author halo
 * 
 */
public class ProvinceCityService extends BaseServiceImpl<ProvinceCityEntity> {

	private static ProvinceCityService provCityService;

	private ProvinceCityDB provCityDb;

	private ProvinceCityService() {
		provCityDb = ProvinceCityDB.getInstance();
		provCityDb.setCollectionName("cn.ohalo.provinceCity");
		setBaseDb(provCityDb);
	}

	public static ProvinceCityService getInstance() {
		if (provCityService == null) {
			provCityService = new ProvinceCityService();
		}
		return provCityService;
	}

	@Override
	public boolean insert(ProvinceCityEntity t) {
		if (StringUtils.isBlank(t.getCode())) {
			t.setCode(Guid.newDCGuid("provCity"));
		}
		t.setCreateDate(new Date());
		return provCityDb.insert(t);
	}

	@Override
	public boolean saveOrUpdate(ProvinceCityEntity t) {
		if (StringUtils.isNotBlank(t.getCode())) {
			return update(t);
		}
		return insert(t);
	}

	@Override
	public boolean update(ProvinceCityEntity t) {
		return provCityDb.update(new BasicDBObject("code", t.getCode()), t);
	}

	@Override
	public List<ProvinceCityEntity> queryAll(ProvinceCityEntity t) {
		return provCityDb.findAll(t);
	}

	@Override
	public List<ProvinceCityEntity> queryAll(ProvinceCityEntity t, int skip,
			int limit) {
		return provCityDb.findAllAndSortAndLimit(t, null, skip, limit);
	}

	@Override
	public Long count(ProvinceCityEntity t) {
		return provCityDb.count(t);
	}
}
