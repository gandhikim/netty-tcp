package com.my.sampleGw;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(App.class);
	
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

		ctx.registerShutdownHook();

    }
}
