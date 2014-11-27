package cn.ohalo.parse.entity;

import cn.ohalo.db.mongodb.MongoBaseEntity;
import cn.ohalo.utils.CodeUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 
 * @author zhaohl
 *
 */
public class HtmlLink extends MongoBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1681210671561402551L;

	public static String ARTICLE = "article";

	public static String ARTICLE_CATEGORY = "articleCategory";

	public static String OTHER = "other";

	public static String TAG = "tag";

	public static String STORE = "store";

	public static Integer COLLECT = 1;

	public static Integer UNCOLLECT = 2;

	private String text;

	private String link;

	private String code;

	private String type;

	/**
	 * 采集状态
	 */
	private Integer state;

	/**
	 * 二级域名
	 */
	private String domain;

	/**
	 * 主要的url地址，主站访问地址
	 */
	private String mainUrlAddress;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "{'text':'" + text + "', 'link':'" + link + "', 'code':'" + code
				+ "', 'type':'" + type + "', 'state':'" + state
				+ "', 'domain':'" + domain + "', 'mainUrlAddress':'"
				+ mainUrlAddress + "'}";
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HtmlLink other = (HtmlLink) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		return true;
	}

	@Override
	public DBObject toDBObject() {

		DBObject db = new BasicDBObject();

		db.put("_id", CodeUtils.encode(link));

		db.put("code", code);
		db.put("text", text);
		db.put("link", link);
		db.put("type", type);

		if (state != null) {
			db.put("state", state);
		}

		db.put("domain", domain);
		db.put("mainUrlAddress", mainUrlAddress);

		return db;
	}

	public String getMainUrlAddress() {
		return mainUrlAddress;
	}

	public void setMainUrlAddress(String mainUrlAddress) {
		this.mainUrlAddress = mainUrlAddress;
	}

}
