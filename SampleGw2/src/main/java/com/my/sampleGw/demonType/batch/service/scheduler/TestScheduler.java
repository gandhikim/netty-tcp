package com.my.sampleGw.demonType.batch.service.scheduler;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import com.my.sampleGw.demonType.batch.service.job.BatchJobTest;
import com.my.sampleGw.demonType.batch.service.jobListener.JobListenerTest;

@Component
public class TestScheduler {
	
	private static final Logger log = Logger.getLogger(TestScheduler.class);

	private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;
    
    @PostConstruct
    public void start() {
    
    	String CronScheduleStr_job1 = "0/5 * * * * ?";
    	String CronScheduleStr_job2 = "0/3 * * * * ?";

    	try {

    		schedulerFactory = new StdSchedulerFactory();
    		scheduler = schedulerFactory.getScheduler();

    		// job 1
    		JobKey jobKey = new JobKey("job1", "group1");
    		JobDetail jobDetail = JobBuilder.newJob(BatchJobTest.class)
    										.withIdentity(jobKey)
    										.build();
    		
    		TriggerKey triggerKey = new TriggerKey("trigger1", "group1");
    		CronTrigger trigger = TriggerBuilder.newTrigger()
    											.withIdentity(triggerKey)
    											.withSchedule(CronScheduleBuilder.cronSchedule(CronScheduleStr_job1))
    											.build();
    		
    		scheduler.scheduleJob(jobDetail, trigger);
    		
    		// job 2
    		JobKey jobKey2 = new JobKey("job2", "group1");
    		JobDetail jobDetail2 = JobBuilder.newJob(BatchJobTest.class)
    										.withIdentity(jobKey2)
    										.build();
    		
    		TriggerKey triggerKey2 = new TriggerKey("trigger2", "group1");
    		Date startDateTime = DateBuilder.futureDate(10, IntervalUnit.SECOND);
    		Date EndDateTime = DateBuilder.futureDate(1, IntervalUnit.MINUTE);
    		CronTrigger trigger2 = TriggerBuilder.newTrigger()
    										.withIdentity(triggerKey2)
    										.startAt(startDateTime)
    										.endAt(EndDateTime)
    										.withSchedule(CronScheduleBuilder.cronSchedule(CronScheduleStr_job2))
    										.build();

    		scheduler.getListenerManager().addJobListener(new JobListenerTest());
    		scheduler.scheduleJob(jobDetail2, trigger2);
    		
    		// 
    		scheduler.start();
    		Thread.sleep(300L * 1000L);
    		scheduler.shutdown(true);
    		
    		
    	}catch (SchedulerException e) {
    		e.printStackTrace();
    	}catch (Exception e) {
    		e.printStackTrace();
		}
        
    }
}
