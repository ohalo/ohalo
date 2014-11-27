package org.ohalo.resource.dao;

import java.util.List;

import org.ohalo.resource.bo.QueryData;
import org.ohalo.resource.pojo.AttachResource;

/**
 * 
 * <pre>
 * 功能：IAttachResourceDao 附加资源的增删改查dao类
 * 修改：赵辉亮
 * 日期：2013-5-16上午11:51:42
 * </pre>
 */
public interface IAttachResourceDao {

	/**
	 * @Description 向数据库中插入附件信息
	 * @param resource
	 * @author 孙升
	 * @date 2012-12-3 上午10:11:08
	 * @version V1.0
	 */
	void insert(AttachResource resource);

	/**
	 * @Description 通过ID或名称查询文件名称和路径
	 * @return
	 * @author 孙升
	 * @date 2012-12-5 上午10:38:22
	 * @version V1.0
	 */
	AttachResource queryFile(String fileId);

	/**
	 * @Description 根据查询条件查询附件列表
	 * @param queryDate
	 * @return
	 * @author 孙升
	 * @date 2012-12-18 上午11:18:35
	 * @version V1.0
	 */
	List<AttachResource> query(QueryData queryData, int start, int limit);

	/**
	 * 
	 * <pre>
	 * 方法体说明：根据条件查询全部附件列表
	 * 作者：赵辉亮
	 * 日期：2013-5-16. 下午2:01:55
	 * </pre>
	 * 
	 * @param queryData
	 *            查询参数
	 * @return
	 */
	List<AttachResource> query(QueryData queryData);

	/**
	 * @Description 根据查询条件查询附件总数
	 * @param queryData
	 * @return
	 * @author 孙升
	 * @date 2012-12-18 上午11:34:43
	 * @version V1.0
	 */
	long count(QueryData queryData);

	/**
	 * @Description 根据GUID删除附件
	 * @param attachGuid
	 * @author 孙升
	 * @date 2012-12-28 上午9:33:55
	 * @version V1.0
	 */
	void deleteByGuid(String attachGuid);

	/**
	 * 通过id进行删除
	 * 
	 * @param String
	 * @return
	 * @author 孙升
	 * @date 2012-12-18 上午11:34:43
	 * @version V1.0
	 */
	void deleteById(String id);

}
