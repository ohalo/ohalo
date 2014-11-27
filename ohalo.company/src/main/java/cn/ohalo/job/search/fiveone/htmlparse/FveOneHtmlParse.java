package cn.ohalo.job.search.fiveone.htmlparse;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import cn.ohalo.job.search.config.ParamsConfig;
import cn.ohalo.job.search.exception.FiveOneJobException;
import cn.ohalo.job.search.fiveone.entity.FiveOneJobSearchResult;

/**
 * 51 job html解析器
 * 
 * @author halo
 * @since 2013-10-5 下午6:06:29
 */
public class FveOneHtmlParse {

	private static Log logger = LogFactory.getLog(FveOneHtmlParse.class);

	/**
	 * 
	 * 这会有一个url地址
	 * 
	 * @param urlAddress
	 */
	public URLConnection connectFiveOneResultList(String urlAddress) {
		URLConnection urlconnection = null;
		try {
			URL url = new URL(urlAddress);
			urlconnection = url.openConnection();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error(FiveOneJobException.FIVEONE_CONNECTION_URL_EXCEPTION,
					e);
			throw new FiveOneJobException(
					FiveOneJobException.FIVEONE_CONNECTION_URL_EXCEPTION);
		} catch (IOException e) {
			logger.error(FiveOneJobException.FIVEONE_IO_EXCEPTION, e);
			throw new FiveOneJobException(
					FiveOneJobException.FIVEONE_IO_EXCEPTION);
		}
		return urlconnection;
	}

	/**
	 * 获取51job的查询url地址
	 * 
	 * @param keyWord
	 *            第一个参数是查询字段
	 * @param cityCode
	 *            城市编码
	 * @param currPage
	 *            当前页
	 * @return
	 */
	public String getFiveOneJobUrlAddress(String keyWord, String cityCode,
			String currPage) {
		String jobUrlAddress = ParamsConfig.FIVEONE_JOB_URL_ADDRESS + "?"
				+ ParamsConfig.FIVEONE_JOB_URL_DEFAULT_PARAMS;
		String jobUrlParams[] = ParamsConfig.FIVEONE_JOB_URL_PARAMS.split(",");
		for (String param : jobUrlParams) {
			if (StringUtils.isBlank(param)) {
				continue;
			}
			if (param.equals("jobarea")) {
				jobUrlAddress += "&jobarea=" + cityCode;
			}
			if (param.equals("keyword")) {
				jobUrlAddress += "&keyword=" + keyWord;
			}
			if (param.equals("curr_page")) {
				jobUrlAddress += "&curr_page=" + currPage;
			}
		}

		return jobUrlAddress;
	}

	/**
	 * 
	 * <pre>
	 * 我建立这个方法的用意是用jobhtml返回这个工作的列表信息，然后根据工作的列表信息查询这些工作
	 * 
	 * 由于这个jobhtml是未经处理的，所以我需要把jobhtml里面的列表单独拿出来处理，就是先把resultList里面的信息拿出来
	 * 
	 * 怎么拿，需要用到htmlParse进行html解析，然后拿到div 的id为resultList的列表，
	 * 
	 * 之后在把div中的table进行分对象储存，由于 51job的显示方式是
	 * 
	 * -----------------------------------------------------------------------------------------------------------------
	 *   职位名称    |   公司名称     |   工作地点      |   更新日期     |  {隐藏字段:jobUrlAddress(工作连接地址),jobCompanyUrlAddress(公司连接地址)}
	 * -----------------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 所以我们需要创建一个对象叫
	 * FiveOneJobSearchResult{jobCode,jobName,companyName,jobAddress,jobUpdateDate,jobUrlAddress,jobCompanyUrlAddress}
	 * 
	 * <h3> 现在遇到两个问题:</h3>
	 * <ul>
	 * 	<li>1.把这些从result拿出来之后，怎么处理呢？
	 * 	<li>2.还有一个问题，之后还有第二页，第三页等等这个要怎么获取。。。。
	 * </ul>
	 * 
	 * 问题1：拿出来之后，应该是保存到一个位置，一个文件或者是插入数据库中（此还有问题，这儿的对象实际上应该是临时保存的信息才是，主要是保存点击每个对象的连接地址才是主要的）
	 * 问题2：已经解决，发现有curr_page可以规定查询那一页，例如curr_page=1查询第一页
	 * </pre>
	 * 
	 * @param jobHtml
	 *            工作的html字符串
	 * @return
	 */
	public List<FiveOneJobSearchResult> returnJobList(
			URLConnection openConenction, String elementId) {

		List<FiveOneJobSearchResult> searchResult = null;
		try {
			Parser parser = new Parser(openConenction);
			String resultListText = getText(elementId, parser);
			searchResult = margerSearchObject(resultListText);
		} catch (ParserException e) {
			logger.error(FiveOneJobException.FIVEONE_IO_EXCEPTION, e);
			throw new FiveOneJobException(
					FiveOneJobException.FIVEONE_IO_EXCEPTION);
		}

		return searchResult;
	}

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
	 * 然后开始拼装查询对象，开始拼装对象之前，会检查接过列表的信息，然后在用htmlParse把相应的信息填充到对象里面，在把对象返回列表
	 * 
	 * 列说明：1.职位名称，2.公司名称，3.工作地点，4.更新日
	 * 
	 * 其中0，1行是无效的行，1行记录title，2行为空。 之后2，3，4§§5，6，7 中间分别有三行记录一个jobinfoResult
	 * 
	 * @param resultListStr
	 *            返回列表字符串
	 * @return
	 */
	private List<FiveOneJobSearchResult> margerSearchObject(String resultListStr) {

		Parser parser = null;
		NodeList tableList = null;
		NodeFilter tableFilter = null;
		List<FiveOneJobSearchResult> results = new ArrayList<FiveOneJobSearchResult>();

		try {
			parser = new Parser(resultListStr);
			tableFilter = new NodeClassFilter(TableTag.class);
			tableList = parser.extractAllNodesThatMatch(tableFilter);
			for (int i = 0; i < tableList.size(); i++) {
				TableTag table = (TableTag) tableList.elementAt(i);
				// 取得表中的行集
				TableRow[] rows = table.getRows();
				// 遍历每行
				for (int r = 0; r < rows.length; r++) {

					if (r == 0 || r == 1) {
						if (logger.isDebugEnabled()) {
							logger.debug("0行和1行为无效的行数，不需要进行解析");
						}
						continue;
					}

					if ((r + 2) % 4 != 0) {
						if (logger.isDebugEnabled()) {
							logger.debug("只有第三行为有效行，所以只进行第三行的解析");
						}
						continue;
					}
					//
					// String a = (r + 2) % 4 != 0 ? "可退出行：" : "不可退出行";
					//
					// System.out.println("行数：" + r + "," + a);

					// if (r % 2 == 1) {
					// continue;
					// }

					TableRow tr = rows[r];
					TableColumn[] td = tr.getColumns();
					FiveOneJobSearchResult result = new FiveOneJobSearchResult();
					// 行中的列
					for (int c = 0; c < td.length; c++) {
						switch (c) {
						case 1:
							result.setJobName(td[c].toPlainTextString());
							result.setJobUrlAddress(toReLink(td[c].toHtml(),
									"job", null));
							break;
						case 2:
							result.setCompanyName(td[c].toPlainTextString());
							result.setJobCompanyUrlAddress(toReLink(
									td[c].toHtml(), "company",
									td[c].toPlainTextString()));
							break;
						case 3:
							result.setJobAddress(td[c].toPlainTextString());
							break;
						case 4:
							result.setJobUpdateDate(td[c].toPlainTextString());
							break;
						default:
							break;
						}
					}
					results.add(result);

					if (logger.isDebugEnabled()) {
						logger.debug("打印从resultlist里面取出来的值:"
								+ result.toString());
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}

		return results;
	}

	private Map<String, String> companymap = new HashMap<String, String>();

	public Map<String, String> getCompanymap() {
		return companymap;
	}

	/**
	 * 
	 * @param htmlStr
	 * @param type
	 * @param companyName
	 * @return
	 */
	public String toReLink(String htmlStr, String type, String companyName) {

		if (StringUtils.equals(type, "company")) {
			String companyurl = companymap.get(companyName);
			if (StringUtils.isNotBlank(companyurl)) {
				return companyurl;
			}
		}

		String linkStr = "";

		try {
			Parser parser = new Parser(htmlStr);
			NodeFilter filter = new TagNameFilter("a");
			NodeList nodelist = parser.extractAllNodesThatMatch(filter);

			for (int i = 0; i < nodelist.size(); i++) {
				LinkTag link = (LinkTag) nodelist.elementAt(i);
				linkStr += link.getLink() + ",";
			}

			linkStr = linkStr.substring(0, linkStr.length() - 1);
		} catch (ParserException e) {
			logger.error(
					FiveOneJobException.FIVEONE_PARSE_JOBORCOMPANYTD_EXCEPTION,
					e);
			throw new FiveOneJobException(
					FiveOneJobException.FIVEONE_PARSE_JOBORCOMPANYTD_EXCEPTION);
		}

		return linkStr;
	}

	public void testParseTd() {
		// String jobtdStr =
		// "<td class=\"td1\"><a href=\"http://search.51job.com/job/57530141,c.html\" onclick=\"zzSearch.acStatRecJob( 1 );\" class=\"jobname\" target=\"_blank\" >软件开发<font color=\"#fe0201\">工程师</font>（<font color=\"#fe0201\">JAVA</font>)</a><img src=\"http://img01.51jobcdn.com/im/2009/search/db_arrow_down.gif\" align=\"absmiddle\" onclick=\"zzSearch.switchListType( this , '#f6f6f6' );\"></td>";
		String companytdStr = "<td class=\"td2\"><a href=\"http://search.51job.com/list/co,c,3110527,000000,10,1.html\" class=\"coname\" target=\"_blank\" >深圳市脸书网络科技有限公司</a></td>";

		try {
			Parser parser = new Parser(companytdStr);

			NodeFilter filter = new TagNameFilter("a");
			NodeList nodelist = parser.extractAllNodesThatMatch(filter);

			for (int i = 0; i < nodelist.size(); i++) {
				LinkTag link = (LinkTag) nodelist.elementAt(i);
				String line = link.getLink();
				System.out.println(line + "," + link.getLinkText());
			}
		} catch (ParserException e1) {
			e1.printStackTrace();
		}

	}

	public void testTable() {
		Parser parser = null;
		NodeList tableList = null;
		NodeFilter tableFilter = null;
		try {
			String html = "<body><table id=’table1′ >"
					+ "<tr><td><A HREF='HTTP://www.baidu.com'>1-11</a></td><td>1-12</td><td>1-13</td>"
					+ "<tr><td>1-21</td><td>1-22</td><td>1-23</td>"
					+ "<tr><td>1-31</td><td>1-32</td><td>1-33</td></table>"
					+ "<table id=’table2′ >"
					+ "<tr><td>2-11</td><td>2-12</td><td>2-13</td"
					+ "<tr><td>2-21</td><td>2-22</td><td>2-23</td>"
					+ "<tr><td>2-31</td><td>2-32</td><td>2-33</td></table>"
					+ "</body>";
			// parser = Parser.createParser(html, "GBK");
			parser = new Parser(html);
			tableFilter = new NodeClassFilter(TableTag.class);
			// tableFilter = new TagNameFilter("TABLE");

			tableList = parser.extractAllNodesThatMatch(tableFilter);

			for (int i = 0; i < tableList.size(); i++) {
				TableTag table = (TableTag) tableList.elementAt(i);
				// 取得表中的行集
				TableRow[] rows = table.getRows();
				// 遍历每行
				for (int r = 0; r < rows.length; r++) {
					TableRow tr = rows[r];
					TableColumn[] td = tr.getColumns();
					// 行中的列
					for (int c = 0; c < td.length; c++) {
						System.out.print("[" + td[c].toPlainTextString() + " "
								+ td[c].getStringText() + "] ");
					}
					System.out.println();

				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 调用这个方法查询51job的工作信息
	 * 
	 * @param keyWord
	 * @param cityCode
	 * @param currPage
	 * @return
	 */
	public List<FiveOneJobSearchResult> searchResult(String keyWord,
			String cityCode, String currPage) {
		String urlAddress = getFiveOneJobUrlAddress(keyWord, cityCode, currPage);
		URLConnection urlConnection = connectFiveOneResultList(urlAddress);
		List<FiveOneJobSearchResult> parseResults = returnJobList(
				urlConnection, ParamsConfig.FIVEONE_JOB_PARSE_DIV_ID);
		return parseResults;
	}

	public static void main1(String[] args) throws UnsupportedEncodingException {
		FveOneHtmlParse parse = new FveOneHtmlParse();
		// parse.testTable();
		//
		String keyWord = URLEncoder.encode("java 工程师", "UTF-8");
		@SuppressWarnings("unused")
		String urlAddress = parse.getFiveOneJobUrlAddress(keyWord, "020000",
				"1");
		URLConnection urlConnection = parse
				.connectFiveOneResultList("http://search.51job.com/jobsearch/search_result.php?fromJs=1&funtype=0000&industrytype=01&keywordtype=1&lang=c&stype=1&postchannel=0000&fromType=1&jobarea=000000&keyword=java&curr_page=1");
		urlConnection.setReadTimeout(30000);
		List<FiveOneJobSearchResult> parseResults = parse.returnJobList(
				urlConnection, "resultList");
		System.out.println(parseResults.size());
		for (FiveOneJobSearchResult fiveOneJobSearchResult : parseResults) {
			System.out.println(fiveOneJobSearchResult.toString());
		}

		// System.out.println(2 % 2 + " ," + 4 % 2 + "," + 1 % 2);

		// parse.testParseTd();
	}

	public static void main(String[] args) throws IOException {
		FveOneHtmlParse parse = new FveOneHtmlParse();
		URLConnection urlConnection = parse
				.connectFiveOneResultList("http://search.51job.com/jobsearch/search_result.php?fromJs=1&funtype=0000&industrytype=01&keywordtype=1&lang=c&stype=1&postchannel=0000&fromType=1&jobarea=000000&keyword=java&curr_page=1");
		urlConnection.setConnectTimeout(6000);
		urlConnection.setReadTimeout(6000);
		InputStream is = urlConnection.getInputStream();
		if(is.available() == 0){
			System.out.println("null");
			return ;
		}else if(is.available() > 0){
			
		}
		System.out.println(urlConnection.getConnectTimeout());
		System.out.println(urlConnection.getContent());

	}

}
