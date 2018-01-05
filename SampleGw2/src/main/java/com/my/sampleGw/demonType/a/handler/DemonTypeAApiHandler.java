package com.my.sampleGw.demonType.a.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.my.sampleGw.demonType.a.service.DemonTypeAService;

@Component("demonTypeAApiHandler")
public class DemonTypeAApiHandler {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( DemonTypeAApiHandler.class );
	
	@Autowired
	@Qualifier("aService")
	DemonTypeAService dmAService;
	
	public String doProcess( String requestMessage ) throws Exception {
		
		log.info( "DemonTypeAApiHandler - doProcess - Start." );
		
		// get Api & parsing msg
		//if( msgType.equals("...") ) {}
		
		String responseMessage = null;
		//responseMessage = "[echo]" + requestMessage;
		responseMessage = dmAService.dmService(requestMessage);
		
		return responseMessage;
	}

}
