package cn.ohalo.company.parse;

/**
 * 公司信息解析
 * 
 * @author z.halo
 * @since 2013年10月9日 1.0
 */
public interface CompanyInfoParse {

	/**
	 * 解析公司html信息返回公司的地址
	 * 
	 * @param companyHtml
	 *            html代码
	 * @return
	 */
	public String parseHtmlReturnAddress(String companyHtml);
}
