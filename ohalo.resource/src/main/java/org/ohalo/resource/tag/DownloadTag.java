package org.ohalo.resource.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.ohalo.resource.util.Attach_Constants;

/**
 * 
 * 
 * <pre>
 * 功能：DownloadTag.java 下载附件的tag标签
 * 作者：赵辉亮
 * 日期：2013-6-25下午6:03:17
 * </pre>
 */
public class DownloadTag extends BodyTagSupport {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 8314226419144069385L;
	// 文件的路径
	private String filePath;
	// 文件的名字
	private String fileName;

	/**
	 * 
	 * <pre>
	 * 方法体说明：(方法详细描述说明、方法参数的具体涵义)
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @return
	 */
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			out.print("<a title=\"下载\" href=\""
					+ Attach_Constants.SYS_DOWNLOAD_URL + filePath + "\" >"
					+ fileName + "</a>");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	/***
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/***
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/***
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/***
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}