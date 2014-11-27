package org.ohalo.favorite.entity;

import java.util.Map;

import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

/**
 * tag 标签，用于存放标签的实体类，有上下级关系，父标签下有子标签
 * 
 * 
 * @author halo
 * 
 */
public class ZTagEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5546025594893264297L;
	// tag's code
	private String tagCode;
	// tag's name
	private String tagName;
	// tag's parent code
	private String tagParentCode;
	// tag's descption
	private String tagDesc;
	
	public ZTagEntity(String tagCode, String tagName, String tagParentCode,
			String tagDesc) {
		super();
		this.tagCode = tagCode;
		this.tagName = tagName;
		this.tagParentCode = tagParentCode;
		this.tagDesc = tagDesc;
	}

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagParentCode() {
		return tagParentCode;
	}

	public void setTagParentCode(String tagParentCode) {
		this.tagParentCode = tagParentCode;
	}

	public String getTagDesc() {
		return tagDesc;
	}

	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}

	@Override
	public String toString() {
		return "ZTagEntity [tagCode=" + tagCode + ", tagName=" + tagName
				+ ", tagParentCode=" + tagParentCode + ", tagDesc=" + tagDesc
				+ ", toString()=" + super.toString() + "]";
	}

	@Override
	public Map<String, Object> toMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBObject toDBObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
