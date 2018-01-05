package com.my.sampleGw.demonType.b.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.my.sampleGw.demonType.b.service.DemonTypeBService;

@Component("demonTypeBApiHandler")
public class DemonTypeBApiHandler {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( DemonTypeBApiHandler.class );
	
	@Autowired
	@Qualifier("bService")
	DemonTypeBService dmBService;
	
	public String doProcess( String requestMessage ) throws Exception {
		
		log.info( "DemonTypeBApiHandler - doProcess - Start." );
		
		// get Api & parsing msg
		//if( msgType.equals("...") ) {}
		
		String responseMessage = null;
		//responseMessage = "[echo]" + requestMessage;
		responseMessage = dmBService.dmService(requestMessage);
		
		return responseMessage;
	}

}
