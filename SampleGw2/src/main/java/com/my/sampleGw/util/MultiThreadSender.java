package com.my.sampleGw.util;

import org.apache.log4j.Logger;

import com.my.sampleGw.NettyClient;
import java.util.concurrent.Callable;

public class MultiThreadSender implements Callable<String> {

	private static final Logger logger = Logger.getLogger(MultiThreadSender.class);
	
	private String msg;
	private NettyClient client;
	
	public void setMsg(String msg, NettyClient client) {
		this.msg = msg;
		this.client = client;
	}
	
	public String call() throws Exception {

		try {

			msg = client.send(this.msg);
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return msg;

	}
	
}
