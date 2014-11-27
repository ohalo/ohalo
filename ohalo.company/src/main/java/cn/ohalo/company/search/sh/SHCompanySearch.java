package cn.ohalo.company.search.sh;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.ohalo.base.utils.LogUtils;

import cn.ohalo.company.parse.CompanyInfoParse;
import cn.ohalo.company.parse.sh.SHCompanyInfoParse;
import cn.ohalo.company.search.CompanySearch;
import cn.ohalo.company.search.exception.SearchException;

/**
 * 
 * 根据公司名称搜索上海的公司信息
 * 
 * @author z.halo
 * 
 */
public class SHCompanySearch implements CompanySearch {

	@Override
	public String searchCompanyAddress(String companyName) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(
				"http://www.sgs.gov.cn/lz/etpsInfo.do?method=doSearch");
		String companyAddress = null;
		try {
			method.setRequestBody(new NameValuePair[] {
					new NameValuePair("keyWords", new String(companyName
							.getBytes(), "ISO8859-1")),
					new NameValuePair("searchType", "1") });
			client.executeMethod(method);
			String response = new String(method.getResponseBody());
			LogUtils.debugParams("SHCompanySearch", "searchCompanyAddress",
					response);
			CompanyInfoParse parse = new SHCompanyInfoParse();
			companyAddress = parse.parseHtmlReturnAddress(response);
			return companyAddress;
		} catch (IllegalArgumentException e) {
			LogUtils.errorMsg("SHCompanySearch", "searchCompanyAddress",
					"查询公司的时候传递参数错误!公司名称:" + companyName, e);
			throw new SearchException("查询公司的时候传递参数错误!公司名称:" + companyName);
		} catch (UnsupportedEncodingException e) {
			LogUtils.errorMsg("SHCompanySearch", "searchCompanyAddress",
					"查询公司的时候传递参数的字符编码有问题。公司名称：" + companyName, e);
			throw new SearchException("查询公司的时候传递参数的字符编码有问题。公司名称：" + companyName);
		} catch (HttpException e) {
			LogUtils.errorMsg("SHCompanySearch", "searchCompanyAddress",
					"查询公司http请求出现异常。公司名称：" + companyName, e);
			throw new SearchException("查询公司http请求出现异常。公司名称：" + companyName);
		} catch (IOException e) {
			LogUtils.errorMsg("SHCompanySearch", "searchCompanyAddress",
					"IOException 读取返回信息出现问题。公司名称：" + companyName, e);
			throw new SearchException("IOException 读取返回信息出现问题。公司名称："
					+ companyName);
		}
	}
}
