package com.my.sampleGw.demonType.batch.service.jobListener;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;


public class JobListenerTest implements JobListener {

	private static final Logger log = Logger.getLogger(JobListenerTest.class);
	
	@Override
	public String getName() {
		log.info("JobListenerTest - getName()");
		return "JobListenerTest";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		log.info("JobListenerTest - jobExecutionVetoed() - "  + arg0.toString());
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext arg0) {
		log.info("JobListenerTest - jobToBeExecuted()" + arg0.toString());
	}

	@Override
	public void jobWasExecuted(JobExecutionContext arg0, JobExecutionException arg1) {
		log.info("JobListenerTest - jobWasExecuted()" + arg0.toString());
		if(arg1 != null) {
			log.info("jobWasExecuted()[" + arg1.toString());
		}
		
	}

}
