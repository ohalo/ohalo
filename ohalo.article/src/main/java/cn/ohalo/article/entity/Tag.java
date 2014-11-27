package cn.ohalo.article.entity;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import cn.ohalo.db.mongodb.MongoBaseEntity;

public class Tag extends MongoBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3159766175318421271L;

	private String name;

	/**
	 * 备忘
	 */
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public DBObject toDBObject() {
		DBObject db = new BasicDBObject();
		db.put("name", name);
		if (StringUtils.isNotBlank(memo)) {
			db.put("memo", memo);
		}
		return db;
	}

}
