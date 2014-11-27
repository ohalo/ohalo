package org.ohalo.resource.service;

import java.util.List;

import org.ohalo.resource.bo.QueryData;
import org.ohalo.resource.pojo.AttachResource;

/**
 * 
 * 
 * <pre>
 * 功能：IResourceService.java
 * 作者：赵辉亮
 * 日期：2013-6-25下午6:03:17
 * </pre>
 */
public interface IResourceService {

	/**
	 * @Description 根据查询条件查询附件列表
	 * 
	 * @param queryDate
	 * @return
	 * @author 孙升
	 * @date 2012-12-18 上午11:18:35
	 * @version V1.0
	 */
	List<AttachResource> query(QueryData queryData, int start, int limit);

	/**
	 * @Description 通过GUID或名称查询文件名称和路径
	 * @return
	 * @author 孙升
	 * @date 2012-12-5 上午10:38:22
	 * @version V1.0
	 */
	AttachResource queryFile(String fileId);

	/**
	 * @Description 根据查询条件查询附件总数
	 * @param queryData
	 * @return
	 * @author 孙升
	 * @date 2012-12-18 上午11:34:43
	 * @version V1.0
	 */
	long count(QueryData queryData);

}
