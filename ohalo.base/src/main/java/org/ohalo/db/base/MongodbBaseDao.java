package org.ohalo.db.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ohalo.base.entity.BaseEntity;
import org.ohalo.base.utils.GenericsUtils;
import org.ohalo.db.HaloDBException;
import org.ohalo.db.impl.MongoHaloDBImpl;
import org.ohalo.db.page.Pagination;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * mongodb　基础操作类
 * 
 * @author z.halo
 * 
 *         2013-1-22下午5:28:26
 */
@SuppressWarnings("unchecked")
public class MongodbBaseDao<T extends BaseEntity> implements BaseDao<T> {

	private static Log logger = LogFactory.getLog(MongoHaloDBImpl.class);

	private MongoTemplate mongoTemplate;

	private Class<T> entityClass;

	/**
	 * 通过条件查询,查询分页结果
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 */
	private Pagination<T> getPage(int pageNo, int pageSize, Query query) {
		long totalCount = this.mongoTemplate
				.count(query, this.getEntityClass());
		Pagination<T> page = new Pagination<T>(pageNo, pageSize, totalCount);
		query.skip(page.getFirstResult());// skip相当于从那条记录开始
		query.limit(pageSize);// 从skip开始,取多少条记录
		List<T> datas = this.find(query);
		page.setDatas(datas);
		return page;
	}

	/**
	 * 通过条件查询实体(集合)
	 * 
	 * @param query
	 */
	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	/**
	 * 通过一定的条件查询一个实体
	 * 
	 * @param query
	 * @return
	 */
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}

	/**
	 * 查询出所有数据
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return this.mongoTemplate.findAll(getEntityClass());
	}

	/**
	 * 查询并且修改记录
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public T findAndModify(Query query, Update update) {
		return this.mongoTemplate.findAndModify(query, update,
				this.getEntityClass());
	}

	/**
	 * 按条件查询,并且删除记录
	 * 
	 * @param query
	 * @return
	 */
	public T findAndRemove(Query query) {
		return this.mongoTemplate.findAndRemove(query, this.getEntityClass());
	}

	public void remove(Object obj) {
		this.mongoTemplate.remove(obj);

	}

	/**
	 * 通过条件查询更新数据
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateFirst(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, this.getEntityClass());
	}

	public void update(Query query, Update update) {
		this.mongoTemplate.upsert(query, update, this.getEntityClass());
	}

	/**
	 * 保存一个对象到mongodb
	 * 
	 * @param bean
	 * @return
	 */
	public void save(T bean) {
		mongoTemplate.save(bean);
	}

	/**
	 * 通过ID获取记录
	 * 
	 * @param id
	 * @return
	 */
	public T findById(String id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	/**
	 * 通过ID获取记录,并且指定了集合名(表的意思)
	 * 
	 * @param id
	 * @param collectionName
	 *            集合名
	 * @return
	 */
	public T findById(String id, String collectionName) {
		return mongoTemplate
				.findById(id, this.getEntityClass(), collectionName);
	}

	/**
	 * 获取需要操作的实体类class
	 * 
	 * @return
	 */
	public Class<T> getEntityClass() {
		if (this.entityClass == null) {
			this.entityClass = GenericsUtils
					.getSuperClassGenricType(MongodbBaseDao.class);
		}
		return this.entityClass;
	};

	/**
	 * 注入mongodbTemplate
	 * 
	 * @param mongoTemplate
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	};

	/***
	 * @return the mongoTemplate
	 */
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	@Override
	public List<T> queryAll() throws HaloDBException {
		return this.mongoTemplate.findAll(getEntityClass());
	}

	@Override
	public List<T> queryByParams(T value) throws HaloDBException {
		return this.queryByParams(value.toMap());
	}

	@Override
	public List<T> queryByParams(Map<String, Object> params) {
		return this.find(toQueryMap(params));
	}

	@Override
	public Pagination<T> queryByParamsLimit(T value, int start, int pageSize)
			throws HaloDBException {
		return this.queryByParamsLimit(value.toMap(), start, pageSize);
	}

	@Override
	public Pagination<T> queryByParamsLimit(Map<String, Object> params,
			int start, int pageSize) throws HaloDBException {

		Pagination<T> page = null;

		try {
			page = this.getPage(start, pageSize, toQueryMap(params));
		} catch (Exception e) {
			throw new HaloDBException("连接数据库出现问题!", e);
		}
		return page;
	}

	@Override
	public void update(T value) throws HaloDBException {
		throw new HaloDBException(
				"this method update(T value) is not allowable !");
	}

	private Query toQueryMap(Map<String, Object> params) {

		if (logger.isInfoEnabled()) {
			logger.info("查询参数是：" + params.toString());
		}

		Query query = new Query();
		Set<String> paramsKey = params.keySet();
		for (Iterator<String> iterator = paramsKey.iterator(); iterator
				.hasNext();) {
			String paramKey = iterator.next();
			query.addCriteria(Criteria.where(paramKey).is(params.get(paramKey)));
		}
		return query;
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
	private Update toUpdateMap(Map<String, Object> params) {
		if (logger.isInfoEnabled()) {
			logger.info("更新参数是：" + params.toString());
		}

		Update update = new Update();

		Set<String> updatesKey = params.keySet();
		for (Iterator<String> iterator = updatesKey.iterator(); iterator
				.hasNext();) {
			String updateKey = iterator.next();
			update.set(updateKey, params.get(updateKey));
		}
		return update;
	}

	@Override
	public void update(String id, Map<String, Object> params) {
		this.mongoTemplate.upsert(Query.query(Criteria.where("id").is(id)),
				toUpdateMap(params), this.getEntityClass());
	}

	@Override
	public void delete(String id) {
		this.mongoTemplate.remove(Query.query(Criteria.where("id").is(id)),
				this.getEntityClass());
	}

	@Override
	public void delete(T value) {
		this.mongoTemplate.remove(value);
	}

	@Override
	public void delete(Map<String, Object> params) {
		this.mongoTemplate.remove(toQueryMap(params), this.getEntityClass());
	}

	@Override
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
}