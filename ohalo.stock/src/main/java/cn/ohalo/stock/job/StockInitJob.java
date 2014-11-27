package cn.ohalo.stock.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class StockInitJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		StockInfoInit into = StockInfoInit.getInstance();
		into.initStockPrice();
		StockRecommend sr = new StockRecommend();
		sr.buyRecommend();
	}

}
