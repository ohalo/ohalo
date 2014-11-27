package cn.ohalo.company.parse.sh;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.ohalo.base.utils.LogUtils;

import cn.ohalo.company.parse.CompanyInfoParse;
import cn.ohalo.company.search.sh.SHCompanySearch;

/**
 * 
 * @author z.halo
 * @since 2013年10月9日 1.0
 */
public class SHCompanyInfoParse implements CompanyInfoParse {

	/**
	 * 获取网页标题和正文组成的文本
	 * 
	 **/
	protected String getText(String elementId, Parser parser)
			throws ParserException {
		NodeFilter TitleFilter = new NodeClassFilter(TitleTag.class);
		NodeFilter ElementIdFilter = new HasAttributeFilter("id", elementId);
		OrFilter orFilter = new OrFilter(TitleFilter, ElementIdFilter); // 做一个逻辑OR
																		// Filter组合
		NodeList list = parser.extractAllNodesThatMatch(orFilter);
		StringBuffer text = new StringBuffer();
		for (int i = 0; i < list.size(); i++)
			text = text.append(list.elementAt(i).toHtml() + "\r\n");
		return text.toString().trim();
	}

	/**
	 * 拿到公司地址
	 * 
	 * @param divLine02
	 * @return
	 */
	public String parseHtmlReturnAddress(String divLine02) {

		Parser parser = null;
		NodeList tableList = null;
		NodeFilter tableFilter = null;

		try {
			parser = new Parser(divLine02);
			tableFilter = new NodeClassFilter(TableTag.class);
			tableList = parser.extractAllNodesThatMatch(tableFilter);
			for (int i = 0; i < tableList.size(); i++) {
				TableTag table = (TableTag) tableList.elementAt(i);
				// 取得表中的行集
				TableRow[] rows = table.getRows();
				// 遍历每行
				for (int r = 0; r < rows.length; r++) {
					TableRow tr = rows[r];
					TableColumn[] td = tr.getColumns();
					for (int a = 0; a < td.length; a++) {
						String tdinfo = td[a].toPlainTextString();
						if (StringUtils.isNotBlank(tdinfo)
								&& tdinfo.indexOf("住所：") > -1) {
							tdinfo = formatCompanyAddress(tdinfo);
							return tdinfo;
						}
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}

		return null;
	}

	protected String formatCompanyAddress(String tdinfo) {
		try {
			tdinfo = tdinfo.trim().substring(
					"住所：".length(),
					tdinfo.indexOf("&nbsp;&nbsp;&nbsp;&nbsp;")
							- "&nbsp;&nbsp;&nbsp;&nbsp;".length() + 5);
		} catch (Exception e) {
			LogUtils.errorMsg("SHCompanyInfoParse", "formatCompanyAddress",
					"公司地址格式化异常!" + tdinfo, e);
			tdinfo = "";
		}
		return tdinfo;
	}

	public static void main(String[] args) {
		SHCompanySearch companySearch = new SHCompanySearch();
		String html = companySearch.searchCompanyAddress("上海雷塔电子科技有限公司");
		System.out.println(html);
	}
}
