package org.ohalo.pomelo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.ohalo.base.service.BaseService;
import org.ohalo.base.utils.TimeUtils;
import org.ohalo.db.page.Pagination;
import org.ohalo.pomelo.config.HomeViewConfig;
import org.ohalo.pomelo.db.ContentDb;
import org.ohalo.pomelo.entity.ContentInfo;
import org.ohalo.pomelo.result.Content;
import org.ohalo.pomelo.result.ContentResult;
import org.ohalo.resource.bo.QueryData;
import org.ohalo.resource.pojo.AttachResource;
import org.ohalo.resource.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author halo
 * @since 2013年11月3日 下午7:27:16
 */
@Service("pomeloContentService")
public class ContentService implements BaseService {

	@Autowired
	private ContentDb contentdb;

	@Resource(name = "attachService")
	private IAttachmentService attachmentService;

	@Autowired
	private HomeViewConfig viewConfig;

	/**
	 * 插入文章
	 * 
	 * @param uaccount
	 *            用户的账号
	 * @param cId
	 *            文章编码
	 * @param ctitle
	 *            文章的标题
	 * @param content
	 *            文章的内容
	 * @param ctype
	 *            文章的类型
	 */
	public void insertContent(String uaccount, String cId, String ctitle,
			String content, String ctype, String issueDate) {
		ContentInfo contentinfo = new ContentInfo(cId, uaccount, content,
				ctitle, ctype, issueDate, null);
		contentinfo.setCreateTime(TimeUtils.dateToString(new Date(),
				TimeUtils.yyyy_MM_dd));
		contentdb.save(contentinfo);
	}

	/**
	 * 插入内容
	 * 
	 * @param contentinfo
	 */
	public void insertContent(ContentInfo contentinfo) {
		contentdb.save(contentinfo);
	}

	/**
	 * 根据文章唯一标识id，删除文章
	 * 
	 * @param cid
	 */
	public void delContent(String cid) {
		contentdb.delete(cid);
	}

	/**
	 * 
	 * 根据用户类型查询用户关联的状态信息
	 * 
	 * @param uAccount
	 *            用户的账号
	 * @param ctype
	 *            文章类型
	 * @param start
	 *            开始位置
	 * @param limit
	 *            每次查询条数
	 * @return
	 */
	public ContentResult queryContentByUser(String uAccount, String ctype,
			Integer start, Integer limit) {
		List<ContentInfo> contents = contentdb.findAllByAccountActypePage(
				uAccount, ctype, start, limit);
		return packageContentInfo(contents);
	}

	/**
	 * 
	 * @param uAccount
	 * @return
	 */
	public ContentResult queryIndexInfo(String uAccount) {
		if (viewConfig == null
				|| StringUtils.isBlank(viewConfig.getHomeViewInfo())) {
			return null;
		}
		/* 其中首页显示的内容的格式为 parent:1* */
		String homeViewInfo = viewConfig.getHomeViewInfo();
		String[] contentParams = homeViewInfo.split("\\&\\#\\$\\%\\&");
		List<ContentInfo> contentArray = new ArrayList<ContentInfo>();
		for (String params : contentParams) {
			String[] ctypeParams = params.split(",");
			ContentInfo contentInfo = new ContentInfo();
			contentInfo.setCtype(ctypeParams[0]);
			contentInfo.setUaccount(uAccount);
			contentInfo.setIssueDate(ctypeParams[3]);
			int start = Integer.parseInt(ctypeParams[1]);
			int limit = Integer.parseInt(ctypeParams[2]);
			List<ContentInfo> contents = contentdb.findAllByContentInfo(
					contentInfo, start, limit);
			if (contents == null || contents.isEmpty()) {
				continue;
			}
			contentArray.addAll(contents);
		}
		return packageContentInfo(contentArray);
	}

	/**
	 * 
	 * 对这个文章进行打包
	 * 
	 * @param contents
	 * @return
	 */
	private ContentResult packageContentInfo(List<ContentInfo> contents) {
		if (contents == null || contents.isEmpty()) {
			return new ContentResult();
		}
		ContentResult result = new ContentResult();
		List<Content> rcontents = new ArrayList<Content>();
		for (Iterator<ContentInfo> iterator = contents.iterator(); iterator
				.hasNext();) {
			Content content = packageContentInfo(iterator.next());
			rcontents.add(content);
		}
		result.setContents(rcontents);
		return result;
	}

	/**
	 * 
	 * @param contentInfo
	 * @return
	 */
	private Content packageContentInfo(ContentInfo contentInfo) {
		Content content = new Content();
		content.setContent(contentInfo);
		QueryData queryData = new QueryData();
		queryData.setAttachguid(contentInfo.getCid());
		List<AttachResource> resource = attachmentService
				.queryAttachResources(queryData);
		if (resource != null && resource.isEmpty()) {
			List<String> paths = new ArrayList<String>();
			for (AttachResource attachResource : resource) {
				paths.add(attachResource.getAttachpath());
			}
			content.setResourcePaths(paths);
		}
		return content;
	}

	/**
	 * 
	 * @param ctype
	 * @param issueDate
	 * @return
	 */
	public ContentResult queryContentByCtypeAdate(String ctype, String issueDate) {
		List<ContentInfo> contents = contentdb.findAllByCtypeAdate(ctype,
				issueDate);
		return packageContentInfo(contents);
	}

	/**
	 * 更新内容信息
	 * 
	 * @param contentInfo
	 */
	public void updateContent(ContentInfo contentInfo) {
		contentdb.update(contentInfo);
	}

	public Pagination<ContentInfo> queryAllContents(ContentInfo contentInfo,
			int start, int limit) {
		Pagination<ContentInfo> p = contentdb.queryByParamsLimit(
				contentInfo.toMap(), start, limit);
		return p;

	}

	/**
	 * 
	 * @param uaccounts
	 * @return
	 */
	public ContentResult queryContentByUnamesOrder(List<String> uaccounts) {
		List<ContentInfo> contents = contentdb
				.queryContentByUnamesOrder(uaccounts);
		return packageContentInfo(contents);
	}

	/**
	 * 
	 * @param uaccounts
	 * @param start
	 * @param limit
	 * @return
	 */
	public ContentResult queryContentByUnamesOrder(List<String> uaccounts,
			int start, int limit) {
		Pagination<ContentInfo> contents = contentdb.queryContentByUnamesOrder(
				uaccounts, start, limit);
		if(contents == null){
			return new ContentResult();
		}
		return packageContentInfo(contents.getDatas());
	}
}
