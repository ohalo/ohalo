package cn.ohalo.parse.db;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ohalo.db.mongodb.BaseDb;
import cn.ohalo.parse.buffer.ISenderBuffer;
import cn.ohalo.parse.entity.HtmlLink;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class HtmlLinkDB extends BaseDb<HtmlLink> implements ISenderBuffer<HtmlLink> {

	private static Log logger = LogFactory.getLog(HtmlLinkDB.class);

	private static HtmlLinkDB htmlLinkDB;

	public static HtmlLinkDB getInstance() {
		if (htmlLinkDB == null) {
			htmlLinkDB = new HtmlLinkDB();
		}

		return htmlLinkDB;
	}

	public HtmlLink findByLink(String link) {
		DBObject db = new BasicDBObject();
		db.put("link", link);

		List<HtmlLink> ls = super.findAll(db);
		if (ls == null || ls.isEmpty()) {
			return null;
		}
		return ls.get(0);
	}

	public boolean update(HtmlLink htmlLink) {
		DBObject db = new BasicDBObject();
		db.put("link", htmlLink.getLink());
		return super.update(db, htmlLink.toDBObject());
	}

	public boolean saveOrUpdate(HtmlLink htmlLink) {
		boolean flag = false;
		try {
			HtmlLink l1 = findByLink(htmlLink.getLink());
			if (l1 == null) {
				flag = insert(htmlLink);
			} else {
				flag = this.update(htmlLink);
			}
		} catch (Exception e) {
			logger.error("save htmllink error！", e);
			flag = false;
		}
		return flag;
	}

	public boolean insert(Collection<HtmlLink> links) {

		boolean flag = true;

		if (links != null && links.size() > 0) {
			for (Iterator<HtmlLink> iterator = links.iterator(); iterator
					.hasNext();) {
				try {
					HtmlLink htmlLink = iterator.next();
					insert(htmlLink);
				} catch (Exception e) {
					flag = false;
				}
			}
		}

		return flag;

	}

	@Override
	public void send(List<HtmlLink> list) {
		insert(list);
	}
}
