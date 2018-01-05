package com.my.sampleGw.demonType.b.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.sampleGw.common.config.SpringConfig;
import com.my.sampleGw.common.util.NettyClient;
import com.my.sampleGw.demonType.b.service.DemonTypeBService;

@Service("bService")
public class DemonTypeBServiceImpl implements DemonTypeBService {

private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DemonTypeBServiceImpl.class);
	
	@Autowired
	@Qualifier("springConfig")
	SpringConfig springConfig;
	
	@Autowired
	@Qualifier("nettyClient")
	NettyClient nettyClient;
	
	@Override
	public String dmService(String requestMsg) throws Exception {
		
		log.info( "DemonTypeBServiceImpl - dmService - Start." );
		
		String responseMsg = null;
		
		// send msg
		
		// exception
		
		// DB job
		try {
			
			//responseMsg = nettyClient.send("127.0.0.1", 12011, requestMsg, 3000);
			
			responseMsg = "[echo]" + requestMsg;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return responseMsg;
	}
}
