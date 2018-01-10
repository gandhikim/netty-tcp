package com.my.sampleGw.demonType.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.sampleGw.common.config.SpringConfig;
import com.my.sampleGw.common.util.MessageUtil;
import com.my.sampleGw.common.util.NettyClient;
import com.my.sampleGw.demonType.org.mapper.OrgMapper;
import com.my.sampleGw.demonType.org.service.OrgService;

@Service("orgServiceImpl")
public class OrgServiceImpl implements OrgService {

	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrgServiceImpl.class);
	
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
	private OrgMapper orgMapper;
	
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
			
			log.info("OrgMapper[" + orgMapper.selectDBConnectTest());
			
			
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return responseMsg;
	}

	
}
