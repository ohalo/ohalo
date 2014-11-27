package cn.ohalo.stock.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.T;

import cn.ohalo.stock.bundle.ConfigBundle;
import cn.ohalo.stock.entity.CompanyStock;
import cn.ohalo.stock.util.StockUtil;

import com.alibaba.fastjson.JSON;

public class StockOprInFile {

	private static Log logger = LogFactory.getLog(StockOprInFile.class);

	private static StockOprInFile sof = null;

	String companyStockUrl;

	String companyStockSynDivId;

	String allStockfileAddress;

	private void initparams() {
		companyStockUrl = ConfigBundle.getString("company_stock_syn_url");
		companyStockSynDivId = ConfigBundle
				.getString("company_stock_syn_divId");
		allStockfileAddress = ConfigBundle.getString("all_stock_fileAddress");
	}

	private StockOprInFile() {
		initparams();
	}

	public static StockOprInFile getInstance() {
		if (sof == null) {
			sof = new StockOprInFile();
		}
		return sof;
	}

	public void setAllStockfileAddress(String allStockfileAddress) {
		this.allStockfileAddress = allStockfileAddress;
	}

	@SuppressWarnings("rawtypes")
	public void insertStock(List stcoks) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(allStockfileAddress)),
					"utf-8"));
			for (Iterator iterator = stcoks.iterator(); iterator.hasNext();) {
				Object dbObject = iterator.next();
				bw.append(dbObject.toString()).append("\r\n");
			}
		} catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

	}

	/**
	 * 插入所有的股票信息，根据url路径和divid，首先根据url找到相应的网站，然后根据divid，找到网上关于股票的代码信息。
	 * 然后把股票代码信息有规则的拼装起来，然后插入文件中
	 * 
	 * @param url
	 * @param divId
	 */
	@SuppressWarnings("rawtypes")
	public void insertAllStock(String url, String divId) {
		if (StringUtils.isNotBlank(url)) {
			companyStockUrl = url;
		}
		if (StringUtils.isNotBlank(divId)) {
			companyStockSynDivId = divId;
		}

		StockUtil su = new StockUtil(companyStockUrl, companyStockSynDivId);

		List stcoks = su.getCompanyStockList();
		insertStock(stcoks);
	}

	@SuppressWarnings({ "resource", "hiding" })
	public <T> List<T> queryAll(Class<T> clazz) {
		List<T> cstocks = new ArrayList<T>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(allStockfileAddress)), "utf-8"));
			String str = null;
			while ((str = br.readLine()) != null) {
				try {
					T cstock = JSON.parseObject(str, clazz);
					cstocks.add(cstock);
				} catch (Exception e) {
					logger.error("打印无法序列化的字符串信息：" + str, e);
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return cstocks;
	}

	@SuppressWarnings("resource")
	public List<CompanyStock> queryAllStock() {
		List<CompanyStock> cstocks = new ArrayList<CompanyStock>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(allStockfileAddress)), "utf-8"));
			String str = null;
			while ((str = br.readLine()) != null) {
				try {
					CompanyStock cstock = JSON.parseObject(str,
							CompanyStock.class);
					cstocks.add(cstock);
				} catch (Exception e) {
					logger.error("打印无法序列化的字符串信息：" + str, e);
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return cstocks;
	}
}