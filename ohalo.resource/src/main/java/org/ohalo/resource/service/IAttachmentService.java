package org.ohalo.resource.service;

import java.util.List;

import org.ohalo.resource.bo.QueryData;
import org.ohalo.resource.pojo.AttachResource;

/**
 * 
 * <pre>
 * 功能：IAttachmentService 附件操作数据库proxy
 * 作者：赵辉亮
 * 日期：2013-5-16上午11:04:36
 * </pre>
 */
public interface IAttachmentService {

	/**
	 * 
	 * <pre>
	 * 方法体说明：保存附件信息
	 * 作者：赵辉亮
	 * 日期：2013-5-16. 上午11:06:08
	 * </pre>
	 * 
	 * @param fileName
	 * @param filePath
	 * @param attachRelId
	 * @param userCode
	 */
	public void insertAttach(AttachResource attach);

	/**
	 * 
	 * <pre>
	 * 方法体说明：查询附件大小，根据附件外关联ID
	 * 作者：赵辉亮
	 * 日期：2013-5-16. 上午11:47:09
	 * </pre>
	 * 
	 * @param attachRelID
	 *            附件关联id
	 * @return
	 */
	public List<String> queryAttachSizes(String attachRelID);

	/**
	 * 
	 * <pre>
	 * 方法体说明：更新附件的关联ID，由原来老的关联id，更新成现在的关联id
	 * 作者：赵辉亮
	 * 日期：2013-7-15. 下午4:06:05
	 * </pre>
	 * 
	 * @param newAttachGuid
	 *            新的附件关联外键编号
	 * @param oldAttachGuid
	 *            老的附件关联外键编号
	 */
	public void updateAttach(String newAttachGuid, String oldAttachGuid);

	/**
	 * 
	 * <pre>
	 * 方法体说明：查询附件
	 * 作者：赵辉亮
	 * 日期：2013-5-16. 下午1:59:51
	 * </pre>
	 * 
	 * @param queryData
	 * @return
	 */
	public List<AttachResource> queryAttachResources(QueryData queryData);

	/**
	 * 
	 * <pre>
	 * 方法体说明：根据附件id，删除附件
	 * 作者：赵辉亮
	 * 日期：2013-5-16. 下午2:05:09
	 * </pre>
	 * 
	 * @param fileID
	 */
	public void deleteAttachById(String fileID);

	/**
	 * 
	 * <pre>
	 * 方法体说明：根据附件关联id删除附件
	 * 作者：赵辉亮
	 * 日期：2013-5-16. 下午2:05:32
	 * </pre>
	 * 
	 * @param attachRelID
	 */
	public void deleteAttachByRelID(String attachRelID);
}
