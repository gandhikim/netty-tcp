package com.my.sampleGw.util;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component("myBean")
public class Scheduler {

	private static final Logger log = Logger.getLogger(Scheduler.class);
	
	int i = 0;
	
	//@Scheduled(fixedDelay = 1000 * 10 * 1) // 밀리세컨드초*초*분 ..단순히 숫자를 넣어도됨
	//@Scheduled(fixedRate = 5000)
	public void schedule() {

		String data = "10초마다 작성.." + i + "번, 시간:" + new Date();
		log.info(data);
		i++;

	}

}