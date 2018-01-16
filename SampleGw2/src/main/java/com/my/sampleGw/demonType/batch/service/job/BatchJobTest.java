package com.my.sampleGw.demonType.batch.service.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BatchJobTest implements Job {

	private static final Logger log = LoggerFactory.getLogger(BatchJobTest.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		log.info("BatchJobTest - execute ======================================================");
		
	}

}
