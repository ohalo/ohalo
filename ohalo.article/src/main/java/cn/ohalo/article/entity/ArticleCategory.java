package cn.ohalo.article.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.ohalo.db.mongodb.MongoBaseEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ArticleCategory extends MongoBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1818545907167635446L;

	private String code;
	private String name;
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	private String treePath;
	private Integer grade;
	private String parentCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	public List<Long> getTreePaths() {
		ArrayList<Long> treePaths = new ArrayList<Long>();
		String[] arrayPath = StringUtils.split(getTreePath(), ",");
		if (arrayPath != null)
			for (String str : arrayPath)
				treePaths.add(Long.valueOf(str));
		return treePaths;
	}

	@Override
	public DBObject toDBObject() {
		
		DBObject db = new BasicDBObject();

		db.put("code", code);
		db.put("name", name);
		db.put("seoTitle", seoTitle);
		db.put("seoKeywords", seoKeywords);
		db.put("seoDescription", seoDescription);
		db.put("treePath", treePath);
		db.put("grade", grade);
		db.put("parentCode", parentCode);

		return db;
	}
}
