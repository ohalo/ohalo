package org.ohalo.pomelo.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ohalo.db.impl.MongoHaloDBImpl;
import org.ohalo.db.page.Pagination;
import org.ohalo.pomelo.entity.ContentInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 内容db
 * 
 * @author halo
 * @since 2013年11月4日 上午11:32:01
 */
public class ContentDb extends MongoHaloDBImpl<ContentInfo> {

	@Override
	protected Class<ContentInfo> getEntityClass() {
		return ContentInfo.class;
	}

	/**
	 * 根据人员账户和文章类型，查询所属的内容信息
	 * 
	 * @param uAccount
	 * @param ctype
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ContentInfo> findAllByAccountActypePage(String uAccount,
			String ctype, Integer start, Integer limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uaccount", uAccount);
		params.put("ctype", ctype);
		Pagination<ContentInfo> infoPage = this.queryByParamsLimit(params,
				start, limit);
		if (infoPage != null) {
			return infoPage.getDatas();
		}
		return null;
	}

	/**
	 * 
	 * @param contentInfo
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ContentInfo> findAllByContentInfo(ContentInfo contentInfo,
			Integer start, Integer limit) {
		Pagination<ContentInfo> infoPage = this.queryByParamsLimit(
				contentInfo.toMap(), start, limit);
		if (infoPage != null) {
			return infoPage.getDatas();
		}
		return null;
	}

	/**
	 * 
	 * @param contentInfo
	 * @return
	 */
	public List<ContentInfo> findAllByContentInfo(ContentInfo contentInfo) {
		return this.queryByParams(contentInfo.toMap());
	}

	/**
	 * 根据ctype获取相关类型的文章，在根据issueDate查询当前日期的文章
	 * 
	 * @param ctype
	 *            新闻，图片，感人文章
	 * @param issueDate
	 *            创建日期
	 * @return
	 */
	public List<ContentInfo> findAllByCtypeAdate(String ctype, String issueDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("issueDate", issueDate);
		params.put("ctype", ctype);
		return this.queryByParams(params);
	}

	/**
	 * 更新文章信息
	 */
	public void update(ContentInfo contentInfo) {
		this.update(
				Query.query(Criteria.where("cid").is(contentInfo.getCid())),
				toUpdateMap(contentInfo.toMap()));
	}

	/**
	 * 根据唯一标识id删除文章
	 * 
	 * @param id
	 */
	public void delete(String id) {
		super.findAndRemove(Query.query(Criteria.where("cid").is(id)));
	}

	/**
	 * 根据多个id查询内容信息
	 * 
	 * @param cids
	 * @return
	 */
	public List<ContentInfo> queryAllByInCidOrder(List<String> cids) {
		if (cids == null || !cids.isEmpty()) {
			return null;
		}
		Sort sort = new Sort(Direction.ASC, "createTime");
		Query query = Query.query(Criteria.where("id").in(cids));
		List<ContentInfo> infos = super.find(query.with(sort));
		return infos;
	}

	/**
	 *
	 * @param uaccounts
	 * @return
	 */
	public List<ContentInfo> queryContentByUnamesOrder(List<String> uaccounts) {
		if (uaccounts == null || !uaccounts.isEmpty()) {
			return null;
		}
		Sort sort = new Sort(Direction.ASC, "createTime");
		Query query = Query.query(Criteria.where("uaccount").in(uaccounts)).with(sort);
		List<ContentInfo> infos = super.find(query.with(sort));
		return infos;
	}
	
	/**
	 *
	 * @param uaccounts
	 * @return
	 */
	public Pagination<ContentInfo> queryContentByUnamesOrder(List<String> uaccounts,int start,int limit) {
		if (uaccounts == null || !uaccounts.isEmpty()) {
			return null;
		}
		Sort sort = new Sort(Direction.ASC, "createTime");
		Query query = Query.query(Criteria.where("uaccount").in(uaccounts)).with(sort);
		Pagination<ContentInfo> infos = super.getPage(start, limit, query);
		return infos;
	}
}
