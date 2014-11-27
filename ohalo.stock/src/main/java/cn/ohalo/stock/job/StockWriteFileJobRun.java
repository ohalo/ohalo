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

public class StockWriteFileJobRun {
	public static void run() {
		try {

			// ①创建一个JobDetail实例，指定SimpleJob
			JobDetail jobDetail = JobBuilder.newJob(StockInitJob.class)
					.withIdentity("testJob_2", "group_2").build();

			Date runTime = DateBuilder.dateOf(16, 0, 0);

			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger_2", "group_2").startAt(runTime)
					.build();
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched;
			sched = sf.getScheduler();
			sched.scheduleJob(jobDetail, trigger);
			sched.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
