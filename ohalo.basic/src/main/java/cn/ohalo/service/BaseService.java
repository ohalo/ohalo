package cn.ohalo.service;

import java.util.List;

import org.bson.types.ObjectId;

import cn.ohalo.db.mongodb.MongoBaseEntity;

import com.mongodb.DBObject;

public interface BaseService<T extends MongoBaseEntity> {

	public boolean insert(T t);

	public boolean saveOrUpdate(T t);

	public boolean update(T t);

	public T queryById(ObjectId _id);

	public List<T> queryAll(T t);

	public List<T> queryAll(T t, int skip, int limit);

	public Long count(T t);

	public void removeAll();

	public void remove(T t);

	public boolean update(DBObject queryParams, T t);
}
