package org.ohalo.db.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ohalo.base.entity.BaseEntity;
import org.ohalo.base.utils.LogUtils;
import org.ohalo.db.HaloDBException;
import org.ohalo.db.IHaloDB;
import org.ohalo.db.base.Mongodb2BaseDao;
import org.ohalo.db.page.Pagination;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 
 * @author halo
 * 
 * @param <T>
 */
public class MongoHaloDBImpl<T extends BaseEntity> extends Mongodb2BaseDao<T>
		implements IHaloDB<T> {

	public List<T> queryAll() throws HaloDBException {
		throw new HaloDBException("this method queryAll is not allowable !");
	}

	public List<T> queryByParams(T value) throws HaloDBException {
		throw new HaloDBException(
				"this method queryByParams is not allowable !");
	}

	public Pagination<T> queryByParamsLimit(T value, int start, int pageSize)
			throws HaloDBException {
		throw new HaloDBException(
				"this method queryByParamsLimit is not allowable !");
	}

	public void update(T value) throws HaloDBException {
		throw new HaloDBException(
				"this method queryByParamsLimit is not allowable !");
	}

	public void delete(String id) {
		super.findAndRemove(Query.query(Criteria.where("id").is(id)));

	}

	public void delete(Map<String, Object> params) {
		super.remove(toQueryMap(params));
	}

	public List<T> queryByParams(Map<String, Object> params) {
		return super.find(toQueryMap(params));
	}

	public Pagination<T> queryByParamsLimit(Map<String, Object> params,
			int start, int pageSize) {
		return super.getPage(start, pageSize, toQueryMap(params));
	}

	public Query toQueryMap(Map<String, Object> params) {
		LogUtils.infoMsg(this.getClass().getName(), "toQueryMap()", "查询参数是："
				+ params.toString());
		Query query = new Query();
		Set<String> paramsKey = params.keySet();
		for (Iterator<String> iterator = paramsKey.iterator(); iterator
				.hasNext();) {
			String paramKey = iterator.next();

			if (null != params.get(paramKey)
					&& !"".equals(params.get(paramKey).toString().trim())) {
				query.addCriteria(Criteria.where(paramKey).is(
						params.get(paramKey)));
			}
		}
		return query;
	}

	@Override
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;

	}

	public void update(String id, Map<String, Object> params) {
		super.update(Query.query(Criteria.where("id").is(id)),
				toUpdateMap(params));
	}

	/**
	 * 
	 * 更新参数转换，由map转换成mongo可以识别的参数类型{@link Update}
	 * 
	 * @param params
	 *            由map组成的参数
	 * @return
	 * 
	 * @see Update
	 */
	public Update toUpdateMap(Map<String, Object> params) {
		LogUtils.infoMsg(this.getClass().getName(), "toUpdateMap()", "更新参数是："
				+ params.toString());
		Update update = new Update();
		Set<String> updatesKey = params.keySet();
		for (Iterator<String> iterator = updatesKey.iterator(); iterator
				.hasNext();) {
			String updateKey = iterator.next();
			if (null != params.get(updateKey)
					&& !"".equals(params.get(updateKey).toString().trim())) {
				update.set(updateKey, params.get(updateKey));
			}
		}
		return update;
	}

	public void delete(T value) {
		super.remove(value);
	}

	@Override
	protected Class<T> getEntityClass() {
		return null;
	}

}
