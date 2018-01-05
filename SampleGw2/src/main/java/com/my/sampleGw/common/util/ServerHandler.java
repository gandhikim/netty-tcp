package com.my.sampleGw.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.my.sampleGw.common.config.SpringConfig;
import com.my.sampleGw.demonType.a.handler.DemonTypeAApiHandler;
import com.my.sampleGw.demonType.b.handler.DemonTypeBApiHandler;
import com.my.sampleGw.org.handler.OrgApiHandler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

@Component("serverHandler")
@Sharable
public class ServerHandler 
		extends SimpleChannelInboundHandler<Object> {

	private final org.slf4j.Logger log 
		= org.slf4j.LoggerFactory.getLogger(ServerHandler.class);
	
	@Autowired
	@Qualifier("springConfig")
	private SpringConfig springConfig;
	
	@Autowired
	@Qualifier("orgApiHandler")
	OrgApiHandler orgApiHandler;
	
	@Autowired
	@Qualifier("demonTypeAApiHandler")
	DemonTypeAApiHandler demonTypeAApiHandler;
	
	
	@Autowired
	@Qualifier("demonTypeBApiHandler")
	DemonTypeBApiHandler demonTypeBApiHandler;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
		String requestMessage = (String) obj;

		String responseMessage = null;

		if ("org".equals(springConfig.getDemonType())) {
			responseMessage = orgApiHandler.doProcess(requestMessage);
		} else if ("a".equals(springConfig.getDemonType())) {
			responseMessage = demonTypeAApiHandler.doProcess(requestMessage);
		} else if ("b".equals(springConfig.getDemonType())) {
			responseMessage = demonTypeBApiHandler.doProcess(requestMessage);
		} else {
			// echo
			responseMessage = "[echo]" + requestMessage;
		}

		ctx.channel()
			.writeAndFlush(responseMessage)
			.addListener(ChannelFutureListener.CLOSE);
		
		log.info("channelRead - msg : " + responseMessage);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

	}
}