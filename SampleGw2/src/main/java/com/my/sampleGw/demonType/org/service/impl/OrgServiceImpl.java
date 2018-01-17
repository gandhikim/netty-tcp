package com.my.sampleGw.demonType.org.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.sampleGw.common.config.SpringConfig;
import com.my.sampleGw.common.util.MessageUtil;
import com.my.sampleGw.common.util.NettyClient;
import com.my.sampleGw.demonType.db.service.DBService;
import com.my.sampleGw.demonType.org.service.OrgService;

@Service("orgServiceImpl")
public class OrgServiceImpl implements OrgService {

	private final Logger log = LoggerFactory.getLogger(OrgServiceImpl.class);
	
	@Autowired
	@Qualifier("springConfig")
	SpringConfig springConfig;
	
	@Autowired
	@Qualifier("nettyClient")
	NettyClient nettyClient;
	
	@Autowired
	@Qualifier("messageUtil")
	MessageUtil messageUtil;
	
	@Autowired
	@Qualifier("dbService")
	DBService dbService;
	
	//@Autowired
	//private OrgMapper orgMapper;
	
	@Override
	public String orgService(String requestMsg) throws Exception {
		
		log.info( "OrgServiceImpl - orgService - Start." );
		
		String responseMsg = null;
		
		// send msg
		
		// exception
		
		// DB job
		try {
			
			//responseMsg = nettyClient.send("127.0.0.1", 12011, requestMsg, 3000);
			//log.info("messageUtil[" + messageUtil.getMessage("name", Locale.ENGLISH));
			responseMsg = "[echo]" + requestMsg;
			
			if("testMsg[6".equals(requestMsg))
				log.info("dbServiceImpl.dbService[" + dbService.dbService("test"));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return responseMsg;
	}

	
}
