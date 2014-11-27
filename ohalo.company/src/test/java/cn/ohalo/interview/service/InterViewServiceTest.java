package cn.ohalo.interview.service;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import cn.ohalo.company.entity.CompanyEntity;
import cn.ohalo.interview.entity.InterViewInfo;

public class InterViewServiceTest extends TestCase {

	private InterViewService interViewService;

	@Override
	protected void setUp() throws Exception {
		interViewService = InterViewService.getInstance();
	}

	public void testInsert() {
		InterViewInfo info = new InterViewInfo();
		info.setCode("abcdefg");
		info.setCompany(new CompanyEntity());
		info.setCreateDate(new Date());
		info.setDesc("这个公司是新的公司");
		info.setJobName("高级java软件工程师");
		info.setSubject("自动生成");
		interViewService.insert(info);
	}

	public void testSaveOrUpdate() {
		InterViewInfo info = new InterViewInfo();
		info.setCode("abcdefg");
		CompanyEntity centity = new CompanyEntity();
		centity.setCompanyAddress("山西路");
		centity.setCompanyCode("0001");
		centity.setCompanyName("西昌公馆");
		info.setCompany(centity);
		info.setCreateDate(new Date());
		info.setDesc("这个公司是新的公司de");
		info.setJobName("高级java软件工程师");
		info.setSubject("自动生成");
		interViewService.saveOrUpdate(info);
	}

	public void testFindAll() {
		InterViewInfo info = new InterViewInfo();
		info.setCode("abcdefg");
		List<InterViewInfo> infos = interViewService.queryAll(info);
		System.out.println(infos.get(0));
	}
}
