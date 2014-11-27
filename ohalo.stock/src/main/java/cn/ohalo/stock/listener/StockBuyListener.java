package cn.ohalo.stock.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.ohalo.stock.job.StockJobRun;
import cn.ohalo.stock.job.StockWriteFileJobRun;

public class StockBuyListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		StockJobRun.run();
		StockWriteFileJobRun.run();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}