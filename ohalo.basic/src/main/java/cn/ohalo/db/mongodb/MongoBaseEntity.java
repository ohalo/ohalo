package cn.ohalo.db.mongodb;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

/**
 * mongodb 数据 对象实体
 * 
 * @author halo
 * 
 */
public abstract class MongoBaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 114951332360381620L;

	private ObjectId _id;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 由 实体转化成DBObject对象，可以被mongodb识别的数据类型
	 * 
	 * @return
	 */
	public abstract DBObject toDBObject();

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public ObjectId get_id() {

		if (_id == null) {
			set_id(ObjectId.get());
		}

		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

}
