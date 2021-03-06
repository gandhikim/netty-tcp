package com.my.sampleGw.demonType.org.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.my.sampleGw.demonType.org.service.OrgService;

@Component("orgApiHandler")
public class OrgApiHandler {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( OrgApiHandler.class );
	
	@Autowired
	@Qualifier("orgServiceImpl")
	private OrgService orgService;
	
	public String doProcess( String requestMessage ) throws Exception {
		
		log.info( "OrgApiHandler - doProcess - Start." );
		
		// get Api & parsing msg
		//if( msgType.equals("...") ) {}
		
		String responseMessage = null;
		//responseMessage = "[echo]" + requestMessage;
		responseMessage = orgService.orgService(requestMessage);
		
		return responseMessage;
	}

}
