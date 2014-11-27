package org.ohalo.pomelo.config;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.ohalo.app.entity.SysConfigInfo;
import org.ohalo.app.service.SysConfigService;
import org.ohalo.base.utils.SpringContextUtil;

public class HomeViewConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5695265656850238793L;

	/**
	 * 首页显示内容
	 */
	private String homeViewInfo;

	public String getHomeViewInfo() {
		if(StringUtils.isBlank(homeViewInfo)){
			initHomeViewInfo();
		}
		return homeViewInfo;
	}

	void initHomeViewInfo() {
		SysConfigService service = (SysConfigService) SpringContextUtil
				.getBean("sysConfigService");
		SysConfigInfo sysConfigInfo = new SysConfigInfo();
		sysConfigInfo.setSysConfigCode("ohalo.pomelo.tohomeView");
		List<SysConfigInfo> infos = service.queryAllSysConfig(sysConfigInfo);
		if (infos != null && !infos.isEmpty()) {
			setHomeViewInfo(infos.get(0).getSysConfigValue());
		}
	}

	public void setHomeViewInfo(String homeViewInfo) {
		this.homeViewInfo = homeViewInfo;
	}

}
