package cn.ohalo.stock.config;

/**
 * 配置信息
 * 
 * @author halo
 * 
 */
public final class Config {

	/**
	 * 表信息：
	 * <ul>
	 * 
	 * <li>company_stock(这个是公司的股票代码表)
	 * <li>stock_code(以股票代码命名的表)
	 * <li>near_${one|two|three}year_stabilitystock(主要记录最近三|两|一年，价格浮动稳定股票)
	 * 
	 * </ul>
	 */
	public static String tables = "company_stock,stock_code,near_${one|two|three}year_stabilitystock";

	/**
	 * <code>
	 * 查询浦发银行2010.09.25 – 2010.10.8之间日线数据
	 * 
	 * http://ichart.yahoo.com/table.csv?s=600000.SS&a=08&b=25&c=2010&d=09&e=8&f=2010&g=d
	 * </code>
	 * 
	 * <ul>
	 * 参数
	 * <li>s — 股票名称
	 * <li>a — 起始时间，月
	 * <li>b — 起始时间，日
	 * <li>c — 起始时间，年
	 * <li>d — 结束时间，月
	 * <li>e — 结束时间，日
	 * <li>f — 结束时间，年
	 * <li>g — 时间周期。Example: g=w, 表示周期是‘周’。d->‘日’(day),
	 * w->‘周’(week)，m->‘月’(mouth)，v->‘dividends only’
	 * 一定注意月份参数，其值比真实数据-1。如需要9月数据，则写为08。
	 * </ul>
	 * 
	 * 如浦发银行的代号是：600000.SS。规则是：上海市场末尾加.ss，深圳市场末尾加.sz。
	 */
	public static String yahooStockUrl = "http://ichart.yahoo.com/table.csv?s={1}&a={2}&b={3}&c={4}&d={5}&e={6}&f={7}&g=d";

	/**
	 * 实时数据请求
	 * http://finance.yahoo.com/d/quotes.csv?s=XOM+BBDb.TO+JNJ+MSFT&f=snd1l1yr
	 * <ul>
	 * 参数
	 * <li>s — 表示股票名称，多个股票之间使用英文加号分隔，如“XOM+BBDb.TO+JNJ+MSFT”，罗列了四个公司的股票：XOM,
	 * BBDb.TO, JNJ, MSFT。
	 * <li>f — 表示返回数据列，如“snd1l1yr”。更详细的参见附录。
	 * </ul>
	 * 
	 */
	public static String yahooStockRTRequestUrl = "http://finance.yahoo.com/d/quotes.csv?s={1}&f={2}";
}
