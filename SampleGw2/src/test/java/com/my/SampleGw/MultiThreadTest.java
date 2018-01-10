package com.my.SampleGw;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.my.sampleGw.util.MultiThreadService;

import junit.framework.TestCase;


public class MultiThreadTest extends TestCase {
	
	MultiThreadService mts;
	List<String> list;
	
	@Before
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		mts = new MultiThreadService();
		mts.setDest("127.0.0.1", 12010, 3000);
		list = new ArrayList<String>();
		/*
		for(int i=0; i < 10; i++) {
			list.add("aaaa");
			list.add("bbbb");
			list.add("cccc");
			list.add("0000");
			list.add("1111");
		}
		*/

	}
	
	@Test
	public void testSend(){
		//mts.provider(list);
	}

}
