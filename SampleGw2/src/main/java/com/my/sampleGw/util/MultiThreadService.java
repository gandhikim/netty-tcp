package com.my.sampleGw.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.my.sampleGw.NettyClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MultiThreadService {

	private static final Logger logger = Logger.getLogger(MultiThreadService.class);
	
	private final int working_timeout_sec = 60;
	private final int return_timeout_sec = 5;
	private final int numberOfThreads = 10;
	
	private String ip;
	private int port;
	private int timeout;

	public void setDest(String ip, int port, int timeout) {
		this.ip = ip;
		this.port = port;
		this.timeout = timeout;
	}
	
	public void provider(List<String> list) {

		ExecutorService executorService = null;
		try {

			//int numberOfThreads = list.size();
			executorService = Executors.newFixedThreadPool(numberOfThreads);
			
			logger.info("send start count : " + list.size());
			
			List<Future<String>> futures = new ArrayList<Future<String>>();
			NettyClient client = new NettyClient(ip, port, timeout);
			
			for (String item : list) {
				MultiThreadSender sender = new MultiThreadSender();
				sender.setMsg(item, client);
				Future<String> future = executorService.submit(sender);
				futures.add(future);
			}
			
			executorService.shutdown();
			
			if (!executorService.awaitTermination(working_timeout_sec, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}

			List<String> successfulList = new ArrayList<String>();
			List<String> failedList = new ArrayList<String>();

			for (Future<String> future : futures) {

				try {
					String rstMap = future.get(return_timeout_sec, TimeUnit.SECONDS);		
					String code = getResult(rstMap);
					if (future.isDone()) {

						if ("0000".equals(code)) {
							successfulList.add(rstMap);
						} else {
							failedList.add(rstMap);
						}

					} else {
						failedList.add(rstMap);
					}
				} catch (InterruptedException e) {
					logger.error("InterruptedException:" + e.getMessage());
				} catch (ExecutionException e) {
					logger.error("ExecutionException:" + e.getMessage());
				} catch (TimeoutException e) {
					logger.error("TimeoutException:" + e.getMessage());
				}

			}

			logger.info("send finish successCnt[" + successfulList.size() 
								+ "] failedCnt[" + failedList.size() + "]");
			
			futures.clear();
			successfulList.clear();
			failedList.clear();
			

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (executorService != null) {
				if (!executorService.isShutdown())
					executorService.shutdownNow();
			}
		}
	}
	
	private String getResult(String msg) {
		return null;
	}
	
}
