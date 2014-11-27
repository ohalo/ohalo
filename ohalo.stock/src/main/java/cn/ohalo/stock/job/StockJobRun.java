package cn.ohalo.stock.job;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class StockJobRun {
	public static void run() {
		try {

			// ①创建一个JobDetail实例，指定SimpleJob
			JobDetail jobDetail = JobBuilder.newJob(StockInitJob.class)
					.withIdentity("testJob_1", "group_1").build();

			Date runTime = DateBuilder.dateOf(9, 30, 30);

			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger_1", "group_1").startAt(runTime)
					.build();
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched;
			sched = sf.getScheduler();
			sched.scheduleJob(jobDetail, trigger);
			sched.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
