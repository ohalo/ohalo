package org.ohalo.app.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ohalo.app.entity.AppInfo;
import org.ohalo.db.impl.MongoHaloDBImpl;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * app应用db
 * 
 * @author halo
 * @since 2013年11月9日 下午6:50:09
 */
public class AppDb extends MongoHaloDBImpl<AppInfo> {

	@Override
	protected Class<AppInfo> getEntityClass() {
		return AppInfo.class;
	}

	/**
	 * 删除应用信息
	 * 
	 * @param appId
	 *            应用标识id
	 */
	public void delete(String appId) {
		super.findAndRemove(Query.query(Criteria.where("appId").is(appId)));
	}

	/**
	 * 更新app应用信息,这个方法用于更新app应用 Pagination
	 * 
	 */
	public void update(AppInfo appInfo) {
		super.update(
				Query.query(Criteria.where("appId").is(appInfo.getAppId())),
				toUpdateMap(appInfo.toMap()));
	}

	/**
	 * 查询应用信息,根据编码查询,因为一个编码下面可能存在这多个版本的应用信息,所以要把所有的应用都拿出来,然后在进行版本的比较
	 * 其中应用类型是指这个版本是在什么智能设备上运行的,有的是在电脑,有的是在手机,有的是平板电脑等等
	 * 
	 * @param appCode
	 *            应用编码
	 * @param appType
	 *            应用类型
	 * @return
	 */
	public List<AppInfo> queryAppInfoByAppCode(String appCode, String appType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appCode", appCode);
		params.put("appType", appType);
		return this.queryByParams(params);
	}
}
