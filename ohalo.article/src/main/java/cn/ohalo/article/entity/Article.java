package cn.ohalo.article.entity;

import java.util.ArrayList;
import java.util.List;

import cn.ohalo.db.mongodb.MongoBaseEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Article extends MongoBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5079754275806923544L;

	private String title;

	/**
	 * 作者
	 */
	private String author;

	private String content;

	private String seoTitle;

	private String seoKeyWords;

	private String seoDescription;

	/**
	 * 是否发布
	 */
	private Boolean isPublication;

	/**
	 * 是否置顶
	 */
	private Boolean isTop;

	private ArticleCategory articleCategory;

	private List<Tag> tags = new ArrayList<Tag>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeyWords() {
		return seoKeyWords;
	}

	public void setSeoKeyWords(String seoKeyWords) {
		this.seoKeyWords = seoKeyWords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public Boolean getIsPublication() {
		return isPublication;
	}

	public void setIsPublication(Boolean isPublication) {
		this.isPublication = isPublication;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<DBObject> getTagDBObjects() {
		List<DBObject> dbTags = new ArrayList<DBObject>();

		if (tags == null || tags.isEmpty()) {
			return dbTags;
		}

		for (Tag tag : tags) {
			dbTags.add(tag.toDBObject());
		}
		return dbTags;
	}

	@Override
	public DBObject toDBObject() {

		DBObject db = new BasicDBObject();

		db.put("_id", get_id());

		db.put("title", title);

		db.put("author", author);

		db.put("content", content);

		db.put("seoTitle", seoTitle);

		db.put("seoKeyWords", seoKeyWords);

		db.put("seoDescription", seoDescription);

		db.put("isPublication", isPublication);

		db.put("isTop", isTop);

		db.put("articleCategory", articleCategory.toDBObject());

		db.put("tags", getTagDBObjects());

		return db;
	}

}
