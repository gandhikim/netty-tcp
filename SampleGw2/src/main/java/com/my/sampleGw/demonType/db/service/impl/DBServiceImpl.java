package com.my.sampleGw.demonType.db.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.sampleGw.demonType.db.service.DBService;
import com.my.sampleGw.demonType.org.mapper.OrgMapper;

@Service("dbService")
public class DBServiceImpl implements DBService {

	private final Logger log = LoggerFactory.getLogger(DBServiceImpl.class);
	
	@Autowired
	private OrgMapper orgMapper;
	
	@Override
	public String dbService(String requestMsg) throws Exception {
		
		log.info( "DBServiceImpl - dbService - Start." );
		
		String responseMsg = null;

		try {

			responseMsg = Integer.toString( orgMapper.selectDBConnectTest() );
			log.info("orgMapper.selectDBConnectTest()[" + responseMsg);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return responseMsg;
	}

}
