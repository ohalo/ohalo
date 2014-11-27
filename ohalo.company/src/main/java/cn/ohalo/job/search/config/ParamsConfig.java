package cn.ohalo.job.search.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author halo
 * @since 2013-10-5 下午5:36:07
 */
public final class ParamsConfig {

	/**
	 * 51job 的url地址
	 */
	public static String FIVEONE_JOB_URL_ADDRESS = "http://search.51job.com/jobsearch/search_result.php";

	public static String FIVEONE_JOB_URL_O_ADDRESS = "http://search.51job.com/list/020000%252C00,000000,0000,01,1,99,java%2B%25B9%25A4%25B3%25CC%25CA%25A6,1,1.html";

	/**
	 * 查询默认参数信息
	 */
	public static String FIVEONE_JOB_URL_DEFAULT_PARAMS = "fromJs=1&funtype=0000&industrytype=01&keywordtype=1&lang=c&stype=1&postchannel=0000&fromType=1";
	/**
	 * <pre>
	 * 
	 * 默认 fromJs=1&funtype=0000&industrytype=01&keywordtype=1&lang=c&stype=1&postchannel=0000&fromType=1
	 * 
	 * keyword 搜寻的关键字 中间空格了用%20代替
	 * jobarea 工作地点:   中间加%2C 可以叠加查询
	 * 	<ul>
	 * 	<li>000000  全国
	 * 	<li>010000 北京
	 * 	<li>020000 上海
	 * 	<li>030000 广州
	 * 	<li>040000 深圳
	 * </ul>
	 * 
	 * curr_page 当前页面码
	 * </pre>
	 * 
	 * @author halo
	 * @since 2013-10-05
	 */
	public static String FIVEONE_JOB_URL_PARAMS = "jobarea,keyword,curr_page";

	/**
	 * 51job 存储工作职位列表的div标签id
	 */
	public static String FIVEONE_JOB_HTML_DIVE_MAKR_RESULTLIST = "resultList";

	/**
	 * 城市与所属编码的对应关系
	 */
	public static Map<String, String> CITYCODES = new HashMap<String, String>();

	/**
	 * html解析51job div的id
	 */
	public static String FIVEONE_JOB_PARSE_DIV_ID = "resultList";

	static {
		CITYCODES.put("全国", "000000");
		CITYCODES.put("北京", "010000");
		CITYCODES.put("上海", "020000");
		CITYCODES.put("广州", "030000");
		CITYCODES.put("深圳", "040000");
	}

}
