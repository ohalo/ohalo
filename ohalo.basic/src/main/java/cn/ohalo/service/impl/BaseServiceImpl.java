package cn.ohalo.service.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import cn.ohalo.db.mongodb.BaseDb;
import cn.ohalo.db.mongodb.MongoBaseEntity;
import cn.ohalo.service.BaseService;

public class BaseServiceImpl<T extends MongoBaseEntity> implements
		BaseService<T> {

	private BaseDb<T> baseDb;

	public void setBaseDb(BaseDb<T> baseDb) {
		this.baseDb = baseDb;
	}

	@Override
	public boolean insert(T t) {
		// TODO Auto-generated method stub
		return baseDb.insert(t);
	}

	@Override
	public boolean saveOrUpdate(T t) {
		// TODO Auto-generated method stub
		return baseDb.saveOrUpdate(t);
	}

	@Override
	public boolean update(T t) {
		// TODO Auto-generated method stub
		return baseDb.update(new BasicDBObject("_id", t.get_id()), t);
	}

	@Override
	public List<T> queryAll(T t) {
		// TODO Auto-generated method stub
		return baseDb.findAll(t);
	}

	@Override
	public List<T> queryAll(T t, int skip, int limit) {
		// TODO Auto-generated method stub
		return baseDb.findAllAndSortAndLimit(t, null, skip, limit);
	}

	@Override
	public Long count(T t) {
		// TODO Auto-generated method stub
		return baseDb.count(t);
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		baseDb.removeAll();
	}

	@Override
	public void remove(T t) {
		// TODO Auto-generated method stub
		baseDb.remove(t);

	}

	@Override
	public boolean update(DBObject queryParams, T t) {
		// TODO Auto-generated method stub
		return baseDb.update(queryParams, t);
	}

	public BaseDb<T> getBaseDb() {
		return baseDb;
	}

	@Override
	public T queryById(ObjectId _id) {
		// TODO Auto-generated method stub
		return baseDb.findById(_id);
	}

}
