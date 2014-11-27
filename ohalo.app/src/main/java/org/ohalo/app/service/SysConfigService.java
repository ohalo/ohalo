package org.ohalo.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.ohalo.app.db.SysConfigDb;
import org.ohalo.app.entity.SysConfigInfo;
import org.ohalo.base.service.BaseService;
import org.ohalo.db.page.Pagination;
import org.springframework.stereotype.Service;

@Service("sysConfigService")
public class SysConfigService implements BaseService {

	@Resource
	private SysConfigDb sysConfigDb;

	public void insertSysConfig(SysConfigInfo sysConfigInfo) {
		sysConfigDb.save(sysConfigInfo);
	}

	public void updateSysConfig(SysConfigInfo sysConfigInfo) {
		sysConfigDb.update(sysConfigInfo);
	}
	
	public void delSysConfig(String sid){
		sysConfigDb.delete(sid);
	}

	public Pagination<SysConfigInfo> queryAllSysConfig(
			SysConfigInfo sysConfigInfo, int start, int limit) {
		Pagination<SysConfigInfo> page = sysConfigDb.queryByParamsLimit(
				sysConfigInfo.toMap(), start, limit);
		return page;
	}

	public List<SysConfigInfo> queryAllSysConfig(SysConfigInfo sysConfigInfo) {
		return sysConfigDb.queryByParams(sysConfigInfo.toMap());
	}
}
