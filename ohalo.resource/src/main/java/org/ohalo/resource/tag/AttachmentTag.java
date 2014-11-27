package org.ohalo.resource.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.ohalo.resource.util.Attach_Constants;

/**
 * 
 * @author z.halo
 * @since 2013年10月12日 1.0
 */
public class AttachmentTag extends BodyTagSupport {

	/**
	 * @Fields serialVersionUID : (附件标签)
	 */

	private static final long serialVersionUID = -8635453955727780541L;
	// 路径
	private String attachPath;
	// 宽度
	private String width;
	// 高度
	private String height;

	/** 图片标签 */
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			out.print("<img src =\"" + Attach_Constants.SYS_URL + attachPath
					+ "\"" + "height = \"" + height + "\"" + "width =\""
					+ width + "\"" + "/>");
		} catch (IOException e) {
			System.out.println("===========" + e.getMessage());
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	/***
	 * @return the attachPath
	 */
	public String getAttachPath() {
		return attachPath;
	}

	/***
	 * @param attachPath
	 *            the attachPath to set
	 */
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	/***
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/***
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/***
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/***
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

}
