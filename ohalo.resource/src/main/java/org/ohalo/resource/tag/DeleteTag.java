package org.ohalo.resource.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.ohalo.resource.util.Attach_Constants;

/**
 * 
 * 
 * <pre>
 * 功能：DeleteTag.java 删除附件的tag java文件，
 * 作者：赵辉亮
 * 日期：2013-6-25下午6:03:17
 * </pre>
 */
public class DeleteTag extends BodyTagSupport {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 535069621246819368L;

	// 附件对外的关联id
	private String attachGuid;

	/**
	 * 
	 * <pre>
	 * 方法体说明：删除附件的开始标签
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @return
	 */
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			out.print("<a title=\"删除\" href=\""
					+ Attach_Constants.SYS_DELETE_URL + attachGuid + "\" >"
					+ "删除</a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	/***
	 * @return the attachGuid
	 */
	public String getAttachGuid() {
		return attachGuid;
	}

	/***
	 * @param attachGuid
	 *            the attachGuid to set
	 */
	public void setAttachGuid(String attachGuid) {
		this.attachGuid = attachGuid;
	}
}