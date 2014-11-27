package org.ohalo.pomelo.result;

import java.io.Serializable;
import java.util.List;

import org.ohalo.pomelo.entity.ContentInfo;

/**
 * 
 * @author z.halo
 * @since 2013年10月14日 1.0
 */
public class Content implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9155158723875159454L;

	private ContentInfo content;

	private List<String> resourcePaths;

	public List<String> getResourcePaths() {
		return resourcePaths;
	}

	public void setResourcePaths(List<String> resourcePaths) {
		this.resourcePaths = resourcePaths;
	}

	public ContentInfo getContent() {
		return content;
	}

	public void setContent(ContentInfo content) {
		this.content = content;
	}
}
