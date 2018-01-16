package com.my.sampleGw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class App 
{
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
    @SuppressWarnings("resource")
	public static void main( String[] args )
    {
    	
        String demonType 	= System.getProperty("demonType");
		String demonEnv 	= System.getProperty("demonEnv");
		String serverNo 	= System.getProperty("serverNo");
		
		log.info("=================================================================================");
		log.info("=  demonType=" + demonType);
		log.info("=  demonEnv=" + demonEnv);
		log.info("=  serverNo=" + serverNo);
		log.info("=  Server Start Success");
		log.info("=================================================================================");

		AbstractApplicationContext ctx = null;

		if("batch".equals(demonType)) {
			
			String[] strArr = new String[3];
			
			strArr[0] = "classpath:batch/job-config.xml";
			strArr[1] = "classpath:batch/scheduler-config.xml";
			
			if("loc".equals(demonEnv)) {
				strArr[2] = "classpath:appContext/applicationContext_loc.xml";
			} else if("dev".equals(demonEnv)) {
				strArr[2] = "classpath:appContext/applicationContext_dev.xml";
			} else if("real".equals(demonEnv)) {
				strArr[2] = "classpath:appContext/applicationContext_real.xml";
			} else if("stg".equals(demonEnv)) {
				strArr[2] = "classpath:appContext/applicationContext_stg.xml";
			} else {
				// org				
				strArr[2] = "classpath:appContext/applicationContext.xml";
			}
			
			ctx = new ClassPathXmlApplicationContext(strArr);
			
		} else {
			
			if("loc".equals(demonEnv)) {
				ctx = new ClassPathXmlApplicationContext("classpath:appContext/applicationContext_loc.xml");
			} else if("dev".equals(demonEnv)) {
					ctx = new ClassPathXmlApplicationContext("classpath:appContext/applicationContext_dev.xml");
			} else if("real".equals(demonEnv)) {
				ctx = new ClassPathXmlApplicationContext("classpath:appContext/applicationContext_real.xml");
			} else if("stg".equals(demonEnv)) {
				ctx = new ClassPathXmlApplicationContext("classpath:appContext/applicationContext_stg.xml");
			} else {
				// org				
				ctx = new ClassPathXmlApplicationContext("classpath:appContext/applicationContext.xml");
			}
			
		}

		ctx.registerShutdownHook();

    }
}
