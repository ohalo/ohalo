package cn.ohalo.stock.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.ohalo.stock.service.StockInfoService;

public class StockWriteFileJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		StockInfoService service = StockInfoService.getInstance();
		service.insertToFile();		
	}

}
