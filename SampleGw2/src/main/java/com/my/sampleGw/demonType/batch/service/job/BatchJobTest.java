package com.my.sampleGw.demonType.batch.service.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class BatchJobTest implements Job {

	private static final Logger log = Logger.getLogger(BatchJobTest.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		log.info("BatchJobTest - execute ======================================================");
		
	}

}
