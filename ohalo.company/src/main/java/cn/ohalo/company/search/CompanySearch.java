package cn.ohalo.company.search;

/**
 * 搜索公司信息
 * 
 * @author z.halo
 * @since 2013-10-09 11:09:05
 */
public interface CompanySearch {

	/**
	 * 根据公司名称搜索公司地址
	 * 
	 * @param companyName
	 * @return
	 */
	public String searchCompanyAddress(String companyName);
}
