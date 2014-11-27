package org.ohalo.app.service;

import java.util.Collections;
import java.util.List;

import org.ohalo.app.db.AppDb;
import org.ohalo.app.entity.AppInfo;
import org.ohalo.base.service.BaseService;
import org.ohalo.db.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @author halo
 * @since 2013年11月9日 下午6:44:11
 */
@Service("appService")
public class AppService implements BaseService {

	@Autowired
	private AppDb appDb;

	/**
	 * 插入应用信息
	 * 
	 * @param appInfo
	 */
	public void insertApp(AppInfo appInfo) {
		appDb.save(appInfo);
	}

	/**
	 * 更新应用信息
	 * 
	 * @param appInfo
	 */
	public void updateApp(AppInfo appInfo) {
		appDb.update(appInfo);
	}

	/**
	 * 删除应用信息
	 * 
	 * @param appId
	 */
	public void deleteApp(String appId) {
		appDb.delete(appId);
	}

	/**
	 * 对查询出来的信息进行分页
	 * 
	 * @param appInfo
	 *            应用
	 * @param start
	 *            开始位置
	 * @param limit
	 *            分页大小
	 * @return
	 */
	public Pagination<AppInfo> queryAllAppLimit(AppInfo appInfo, int start,
			int limit) {
		Pagination<AppInfo> pageInfos = appDb.queryByParamsLimit(
				appInfo.toMap(), start, limit);
		return pageInfos;
	}

	/**
	 * 根据应用类型编码,传递回最新的应用路径信息
	 * 
	 * @param appCode
	 *            应用编码
	 * @param appType
	 *            应用类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public AppInfo queryNewVersionApp(String appCode, String appType) {
		List<AppInfo> apps = appDb.queryAppInfoByAppCode(appCode, appType);

		if (apps == null || apps.isEmpty()) {
			return null;
		}

		Collections.sort(apps, new AppInfo());
		return apps.get(apps.size() - 1);
	}
}
