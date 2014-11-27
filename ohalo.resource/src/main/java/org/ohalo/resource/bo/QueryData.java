package org.ohalo.resource.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author z.halo
 * @since 2013年10月12日 1.0
 */
public class QueryData {

	// 文件id
	private String fileId;

	// 附件name
	private String attachName;

	// 附件关联外键id
	private String attachguid;

	// 创建用户
	private String createUser;

	// 开始时间
	private Date startDate;

	// 结束时间
	private Date endDate;

	// 时间格式化
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// calender
	private Calendar calendar = new GregorianCalendar();

	/**
	 * 
	 * <pre>
	 * 方法体说明：get() 附件名称
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @return
	 */
	public String getAttachName() {
		return attachName;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：设置附件名称
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param attachName
	 */
	public void setAttachName(String attachName) {

		if (StringUtils.isNotBlank(attachName)) {
			this.attachName = attachName;
		}
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：get 创建用户名称
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @return
	 */
	public String getCreateUser() {

		return createUser;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：设置创建用户名称
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param createUser
	 */
	public void setCreateUser(String createUser) {

		if (StringUtils.isNotBlank(createUser)) {
			this.createUser = createUser;
		}

	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：get start date
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @return
	 */
	public Date getStartDate() {

		return startDate;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：设置开始日期
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param startDate
	 */
	public void setStartDate(String startDate) {

		if (StringUtils.isNotBlank(startDate)) {
			try {
				this.startDate = format.parse(startDate);
			} catch (ParseException e) {
				// TODO 添加转换异常类
				e.printStackTrace();

			}
		}

	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：get 结束日期
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @return
	 */
	public Date getEndDate() {

		return endDate;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：设置结束日期
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param endDate
	 */
	@SuppressWarnings("static-access")
	public void setEndDate(String endDate) {

		if (StringUtils.isNotBlank(endDate)) {
			try {
				Date date = format.parse(endDate);
				calendar.setTime(date);
				calendar.add(calendar.DATE, 1);
				this.endDate = calendar.getTime();
			} catch (ParseException e) {
				// TODO 添加转换异常类
				e.printStackTrace();

			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：get 附件关联外键ID
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @return
	 */
	public String getAttachguid() {
		return attachguid;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：set 附件外键关联id
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param attachguid
	 */
	public void setAttachguid(String attachguid) {
		this.attachguid = attachguid;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：get file id method 
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @return
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：set file id method 
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param fileId
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
