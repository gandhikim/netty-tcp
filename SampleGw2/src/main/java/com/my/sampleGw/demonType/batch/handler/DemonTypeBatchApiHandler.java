package com.my.sampleGw.demonType.batch.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.my.sampleGw.demonType.batch.service.DemonTypeBatchService;

@Component("demonTypeBatchApiHandler")
public class DemonTypeBatchApiHandler {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( DemonTypeBatchApiHandler.class );
	
	@Autowired
	@Qualifier("batchService")
	DemonTypeBatchService dmBatchService;
	
	public String doProcess( String requestMessage ) throws Exception {
		
		log.info( "DemonTypeBatchApiHandler - doProcess - Start." );
		
		// get Api & parsing msg
		//if( msgType.equals("...") ) {}
		
		String responseMessage = null;
		//responseMessage = "[echo]" + requestMessage;
		responseMessage = dmBatchService.dmService(requestMessage);
		
		return responseMessage;
	}

}
