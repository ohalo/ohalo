package org.ohalo.app.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ohalo.app.config.ParamsConfig;
import org.ohalo.app.entity.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AppServiceTest {

	@Autowired
	public AppService appService;

	@Test
	public void testQueryNewVersoinApp() {
		AppInfo appInfo = appService.queryNewVersionApp(
				ParamsConfig.APP_CODE_POMELO,
				ParamsConfig.APP_TYPE_IOS_PHONE);
		//System.out.println(appInfo.toString());
	}
}