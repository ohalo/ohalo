package cn.ohalo.db.mongodb;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;

import com.alibaba.fastjson.util.TypeUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * 
 * @author halo
 * 
 */
public class BaseDb<T extends MongoBaseEntity> {

	public static Log logger = LogFactory.getLog(BaseDb.class);

	private DBCollection collection;

	private String collectionName;

	private Class<T> entityClass;

	public String getCollectionName() {
		return collectionName;
	}

	public void initDBCollection() {
		MongoConnection.initMongodb();
		MongoConnection.setWriteConcern(WriteConcern.SAFE);
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
		collection = MongoConnection.initCollection(collectionName);
	}

	public DBCollection getCollection() {
		return collection;
	}

	@SuppressWarnings("unchecked")
	public BaseDb() {
		initDBCollection();
		Type genericType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genericType)
				.getActualTypeArguments();
		entityClass = (Class<T>) params[0];
	}

	public T findOne() {
		DBObject obj = collection.findOne();
		if (obj == null) {
			return null;
		}
		return TypeUtils.castToJavaBean(obj.toMap(), entityClass);
	}

	public T findById(ObjectId _id) {
		DBObject params = new BasicDBObject();
		params.put("_id", _id);
		DBObject obj = collection.findOne(params);
		if (obj == null) {
			return null;
		}
		return TypeUtils.castToJavaBean(obj.toMap(), entityClass);
	}

	/**
	 * 根据参数查询所有的文档信息
	 * 
	 * @param params
	 *            查询参数
	 * @return 返回文档集合
	 */
	public List<T> findAll(DBObject params) {
		return findAllAndSortAndLimit(params, null, null, null);
	}

	/**
	 * 根据参数查询所有的文档信息
	 * 
	 * @param params
	 *            查询参数
	 * @return 返回文档集合
	 */
	public List<T> findAll(T t) {
		return findAll(t == null ? null : t.toDBObject());
	}

	/**
	 * 查询所有的文档信息，并进行排序
	 * 
	 * @param params
	 *            查询参数
	 * @param sortparams
	 *            排序参数 1为正序排列 ， -1为倒序排列
	 * @return 返回文档集合
	 */
	public List<T> findAllAndSort(DBObject params, DBObject sortparams) {
		return findAllAndSortAndLimit(params, sortparams, null, null);
	}

	/**
	 * 查询所有的文档信息，并进行排序
	 * 
	 * @param params
	 *            查询参数
	 * @param sortparams
	 *            排序参数 1为正序排列 ， -1为倒序排列
	 * @return 返回文档集合
	 */
	public List<T> findAllAndSort(T t, DBObject sortparams) {
		return findAllAndSort(t == null ? null : t.toDBObject(), sortparams);
	}

	/**
	 * 查询符合条件的文档数量
	 * 
	 * @param params
	 *            查询参数
	 * @return
	 */
	public Long count(DBObject params) {
		return collection.count(params);
	}

	/**
	 * 查询符合条件的文档数量
	 * 
	 * @param params
	 *            查询参数
	 * @return
	 */
	public Long count(T t) {
		return count(t == null ? null : t.toDBObject());
	}

	/**
	 * 查询所有的文档记录信息，并且对这些信息进行排序和分页
	 * 
	 * @param params
	 *            查询参数
	 * @param sortparams
	 *            排序参数
	 * @param skip
	 *            从第几个位置开始
	 * @param limit
	 *            查询几条记录
	 * @return
	 */
	public List<T> findAllAndSortAndLimit(DBObject params, DBObject sortparams,
			Integer skip, Integer limit) {
		DBCursor cursor = null;
		if (params == null) {
			cursor = collection.find();
		} else {
			cursor = collection.find(params);
		}

		if (sortparams != null) {
			cursor = cursor.sort(sortparams);
		}

		if (skip != null) {
			cursor = cursor.skip(skip);
		}

		if (limit != null && limit > 0) {
			cursor = cursor.limit(limit);
		}

		List<T> list = new ArrayList<T>();
		while (cursor.hasNext()) {
			T t = TypeUtils.castToJavaBean(cursor.next().toMap(), entityClass);
			list.add(t);
		}
		return list;
	}

	/**
	 * 查询所有的文档记录信息，并且对这些信息进行排序和分页
	 * 
	 * @param params
	 *            查询参数
	 * @param sortparams
	 *            排序参数
	 * @param skip
	 *            从第几个位置开始
	 * @param limit
	 *            查询几条记录
	 * @return
	 */
	public List<T> findAllAndSortAndLimit(T t, DBObject sortparams,
			Integer skip, Integer limit) {
		return findAllAndSortAndLimit(t == null ? null : t.toDBObject(),
				sortparams, skip, limit);
	}

	/**
	 * 查询多条文档
	 * 
	 * @param objs
	 *            文档集合
	 * @return 返回插入的文档成功或者是失败 ，true 为成功， false 为失败
	 */
	public boolean insert(List<DBObject> objs) {
		boolean flag = false;
		try {
			WriteResult result = collection.insert(objs);
			if (result.getN() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("插入数据库出现异常,数据库名称：" + this.getCollectionName()
					+ ",插入参数：" + objs == null ? "null" : objs.toString(), e);
		}
		return flag;
	}

	/**
	 * 插入单条文档
	 * 
	 * @param obj
	 *            单个文档
	 * @return 返回插入的文档成功或者是失败 ，true 为成功， false 为失败
	 */
	public boolean insert(DBObject obj) {
		boolean flag = false;
		try {
			obj.put("createDate", new Date());
			WriteResult result = collection.insert(obj);
			if (result.getN() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("插入数据库出现异常,数据库名称：" + this.getCollectionName()
					+ ",插入参数：" + obj == null ? "null" : obj.toString(), e);
		}
		return flag;
	}

	/**
	 * 插入更新数据库
	 * 
	 * @param obj
	 * @return
	 */
	public boolean saveOrUpdate(DBObject obj) {
		boolean flag = false;
		try {
			WriteResult result = collection.save(obj);
			if (result.getN() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("插入数据库或者是更新数据库出现异常,数据库名称：" + this.getCollectionName()
					+ ",插入参数：" + obj == null ? "null" : obj.toString(), e);
		}
		return flag;
	}

	/**
	 * 插入更新数据库
	 * 
	 * @param obj
	 * @return
	 */
	public boolean saveOrUpdate(T t) {
		return saveOrUpdate(t == null ? null : t.toDBObject());
	}

	/**
	 * 插入单条文档
	 * 
	 * @param obj
	 *            单个文档
	 * @return 返回插入的文档成功或者是失败 ，true 为成功， false 为失败
	 */
	public boolean insert(T t) {
		return insert(t == null ? null : t.toDBObject());
	}

	/**
	 * 更新文档信息
	 * 
	 * @param queryParams
	 *            查询参数
	 * @param obj
	 *            更新文档
	 * @return 返回插入的文档成功或者是失败 ，true 为成功， false 为失败
	 */
	public boolean update(DBObject queryParams, DBObject obj) {
		boolean flag = false;
		try {
			obj.put("modifyDate", new Date());
			WriteResult result = collection.update(queryParams,
					new BasicDBObject().append("$set", obj), true, true);
			if (result.getN() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error(
					"更新数据库出现异常,数据库名称：" + this.getCollectionName() + ",更新参数："
							+ obj == null ? "null" : obj.toString() + ",查询参数："
							+ queryParams == null ? "null" : queryParams
							.toString(), e);
		}
		return flag;
	}

	/**
	 * 更新文档信息
	 * 
	 * @param queryParams
	 *            查询参数
	 * @param obj
	 *            更新文档
	 * @return 返回插入的文档成功或者是失败 ，true 为成功， false 为失败
	 */
	public boolean update(DBObject queryParams, T t) {
		return update(queryParams, t == null ? null : t.toDBObject());
	}

	/**
	 * 删除数据库的文档信息
	 * 
	 * @param obj
	 * @return
	 */
	public boolean remove(DBObject obj) {
		boolean flag = false;
		try {
			WriteResult result = collection.remove(obj);
			if (result.getN() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("删除数据出现异常,数据库名称：" + this.getCollectionName()
					+ ",删除参数：" + obj == null ? "null" : obj.toString(), e);
		}
		return flag;
	}

	/**
	 * 删除数据库的文档信息
	 * 
	 * @param obj
	 * @return
	 */
	public boolean remove(T t) {
		return remove(t == null ? null : t.toDBObject());
	}

	/**
	 * 删除这个collection所有文档信息
	 */
	public void removeAll() {
		collection.drop();
	}
}
