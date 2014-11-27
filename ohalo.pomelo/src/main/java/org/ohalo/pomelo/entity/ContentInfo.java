package org.ohalo.pomelo.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

/**
 * 
 * @author z.halo
 * @since 2013年10月14日 1.0
 */
public class ContentInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7652235303896400096L;

	private String cid;

	/**
	 * 账户名称
	 */
	public String uaccount;

	/**
	 * 内容文本
	 */
	public String cText;

	/**
	 * 内容标题
	 */
	public String cTitle;

	/**
	 * 内容类型，图片，新闻
	 */
	private String ctype;

	/**
	 * 发布时间， 格式：2013-10-16
	 */
	private String issueDate;

	public ContentInfo(String cid, String uaccount, String cText,
			String cTitle, String ctype, String issueDate, List<String> tags) {
		super();
		this.cid = cid;
		this.uaccount = uaccount;
		this.cText = cText;
		this.cTitle = cTitle;
		this.tags = tags;
		this.ctype = ctype;
		this.issueDate = issueDate;
	}

	public ContentInfo() {
		super();
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> tags;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getUaccount() {
		return uaccount;
	}

	public void setUaccount(String uaccount) {
		this.uaccount = uaccount;
	}

	public String getcText() {
		return cText;
	}

	public void setcText(String cText) {
		this.cText = cText;
	}

	public String getcTitle() {
		return cTitle;
	}

	public void setcTitle(String cTitle) {
		this.cTitle = cTitle;
	}

	/**
	 * @return the ctype
	 */
	public String getCtype() {
		return ctype;
	}

	/**
	 * @param ctype
	 *            the ctype to set
	 */
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	@Override
	public String toString() {
		return "ContentInfo [cid=" + cid + ", uaccount=" + uaccount
				+ ", cText=" + cText + ", cTitle=" + cTitle + ", ctype="
				+ ctype + ", issueDate=" + issueDate + ", tags=" + tags + "]";
	}

	public Map<String, Object> toMap() {
		Map<String, Object> contentInfo = new HashMap<String, Object>();

		contentInfo.put("cid", cid);
		contentInfo.put("uaccount", uaccount);
		contentInfo.put("cText", cText);
		contentInfo.put("cTitle", cTitle);
		contentInfo.put("ctype", ctype);
		contentInfo.put("issueDate", issueDate);
		contentInfo.put("tags", tags);
		contentInfo.put("createTime", getCreateTime());

		return contentInfo;
	}

	@Override
	public DBObject toDBObject() {
		// TODO Auto-generated method stub
		return null;
	}

}