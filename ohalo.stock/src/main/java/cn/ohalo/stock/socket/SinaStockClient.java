package cn.ohalo.stock.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SinaStockClient {

	private static Log logger = LogFactory.getLog(SinaStockClient.class);

	private static SinaStockClient client = null;

	private Set<String> errorStockSet = new HashSet<String>();

	public static SinaStockClient getInstance() {
		if (client == null) {
			client = new SinaStockClient();
		}

		return client;
	}

	/**
	 * 实时获取股票信息 ：sh 标识上海证券，sz标识深圳证券 ，后面的是股票代码
	 * http://hq.sinajs.cn/list=sh601006,sz000002,sz000004
	 * 
	 * 获取数据格式：<br>
	 * <ul>
	 * <li>var hq_str_sh601006=
	 * "大秦铁路,7.34,7.32,7.35,7.44,7.29,7.35,7.36,23283269,171342101,51260,7.35,123619,7.34,275000,7.33,64000,7.32,39502,7.31,34408,7.36,70381,7.37,100200,7.38,38087,7.39,48518,7.40,2013-12-27,15:03:12,00"
	 * ;
	 * <li>var hq_str_sz000002=
	 * "万  科Ａ,7.85,7.80,8.03,8.10,7.81,8.02,8.03,73964223,590192386.36,85536,8.02,133914,8.01,985491,8.00,251977,7.99,556471,7.98,336735,8.03,750450,8.04,249847,8.05,243786,8.06,595506,8.07,2013-12-27,15:05:51,00"
	 * ;
	 * <li>var hq_str_sz000004=
	 * "国农科技,11.23,11.21,11.55,11.55,11.18,11.52,11.55,840971,9609900.07,13400,11.52,3800,11.51,400,11.50,3300,11.47,1800,11.46,2510,11.55,1400,11.56,700,11.57,5200,11.58,22700,11.59,2013-12-27,15:05:51,00"
	 * 
	 * </ul>
	 * 
	 * <pre>
	 * 这个字符串由许多数据拼接在一起，不同含义的数据用逗号隔开了，按照程序员的思路，顺序号从0开始。
	 * 0：”大秦铁路”，股票名字；
	 * 1：”27.55″，今日开盘价；
	 * 2：”27.25″，昨日收盘价；
	 * 3：”26.91″，当前价格；
	 * 4：”27.55″，今日最高价；
	 * 5：”26.20″，今日最低价；
	 * 6：”26.91″，竞买价，即“买一”报价；
	 * 7：”26.92″，竞卖价，即“卖一”报价；
	 * 8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
	 * 9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
	 * 10：”4695″，“买一”申请4695股，即47手；
	 * 11：”26.91″，“买一”报价；
	 * 12：”57590″，“买二”
	 * 13：”26.90″，“买二”
	 * 14：”14700″，“买三”
	 * 15：”26.89″，“买三”
	 * 16：”14300″，“买四”
	 * 17：”26.88″，“买四”
	 * 18：”15100″，“买五”
	 * 19：”26.87″，“买五”
	 * 20：”3100″，“卖一”申报3100股，即31手；
	 * 21：”26.92″，“卖一”报价
	 * (22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
	 * 30：”2008-01-11″，日期；
	 * 31：”15:05:32″，时间；
	 * </pre>
	 * 
	 */
	public static String sinastockurl = "http://hq.sinajs.cn/list=";

	public static String SH = "sh";
	public static String SZ = "sz";

	private static Map<String, Double> currentPrices = new HashMap<String, Double>();

	private void init(List<String> stockCodes) {
		List<String> codes = new ArrayList<String>();
		int i = 0;
		for (String code : stockCodes) {
			if (errorStockSet.contains(code)) {
				continue;
			} else {
				codes.add(code);
				i++;
			}

			if (i == 9) {
				getCurrentStockPrice(codes);
				i = 0;
				codes.clear();
			}
		}

		getCurrentStockPrice(codes);
	}

	public void setStockCodes(final List<String> stockCodes) {
		init(stockCodes);
	}

	public Map<String, Double> getCurrentPrices() {
		return currentPrices;
	}

	public Double getCurrentStockPricebyCode(String stockCode) {
		if (currentPrices == null) {
			currentPrices = new HashMap<String, Double>();
			currentPrices.put(stockCode, 0.0);
		}
		return currentPrices.get(stockCode);
	}

	private BufferedReader getCurrentSocketBufferedReader(String charsetName,
			List<String> stockCodes) {

		String newUrl = sinastockurl;

		for (String stockCode : stockCodes) {
			newUrl = newUrl + stockCode + ",";
		}

		newUrl = newUrl.substring(0, newUrl.length() - 1);

		if (logger.isDebugEnabled()) {
			logger.debug("股票访问地址：" + newUrl);
		}

		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(newUrl);
			URLConnection conn = url.openConnection();
			bufferedReader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charsetName));
		} catch (MalformedURLException e) {
			logger.error("URL协议、格式或者路径错误，url地址：" + newUrl
					+ ",可能存在错误原因：路径问题，最好不要包含中文路径，因为有时中文路径会乱码，导致无法识别");
		} catch (IOException e) {
			logger.error("URLConnection 获取连接出现异常，流读取异常，可能存在原因：io输出问题，断网或者url地址出现问题，url地址为："
					+ newUrl);
		}
		return bufferedReader;
	}

	private void getCurrentStockPrice(List<String> stockCodes) {
		if (stockCodes == null) {
			logger.error("stockCodes 为空，请重新传值进来，传值参数格式为{sz|sh}开头，后面跟股票代码");
			return;
		}

		BufferedReader br = getCurrentSocketBufferedReader("GBK", stockCodes);

		if (br == null) {
			return;
		}

		String temp = null;
		String stockcode = null;
		try {
			while ((temp = br.readLine()) != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("读取股票数据信息：" + temp);
				}
				stockcode = temp.substring(
						temp.indexOf("hq_str_") + "hq_str_".length(),
						temp.indexOf("=\""));
				String a = temp.substring(temp.indexOf("=\"") + "=\"".length(),
						temp.lastIndexOf("\""));
				String[] astrs = a.split(",");
				if (astrs != null && astrs.length > 3) {
					currentPrices.put(stockcode, Double.parseDouble(astrs[3]));
				} else {
					errorStockSet.add(stockcode);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("数组下标越界！信息读取失败：" + temp == null ? "null" : temp, e);
			errorStockSet.add(stockcode);
		} catch (IOException e) {
			logger.error("http访问请求的io信息异常！信息读取失败：" + temp == null ? "null"
					: temp);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("http访问请求的io流无法关闭！");
				}
			}
		}

	}

}
