package org.ohalo.app.result;

import org.ohalo.app.entity.AppInfo;
import org.ohalo.base.data.Result;

/**
 * 
 * @author halo
 * @since 2013年11月10日 上午8:19:40
 */
public class AppResult extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2082564592128036459L;

	private AppInfo appInfo;

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}

}
