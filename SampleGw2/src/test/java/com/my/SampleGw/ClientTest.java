package com.my.SampleGw;

import org.junit.Before;
import org.junit.Test;

import com.my.sampleGw.common.util.NettyClient;

import junit.framework.TestCase;


public class ClientTest extends TestCase {

	NettyClient client;
	
	@Before
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();	
		client = new NettyClient();

	}
	
	@Test
	public void testSend(){
		try {

			for(int i=0; i<10; i++){
				assertEquals(
						"[echo]testMsg[" + Integer.toString(i)
						, client.send("127.0.0.1", 12010, "testMsg[" + Integer.toString(i), 3000)
						);	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
