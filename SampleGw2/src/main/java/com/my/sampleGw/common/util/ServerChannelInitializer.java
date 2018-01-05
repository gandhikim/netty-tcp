package com.my.sampleGw.common.util;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

@Component("serverChannelInitializer")
public class ServerChannelInitializer
		extends ChannelInitializer<SocketChannel> {

	@Autowired
	@Qualifier("stringDecoder")
	StringDecoder stringDecoder;
	
	@Autowired
	@Qualifier("stringEncoder")
	StringEncoder stringEncoder;

	@Autowired
	@Qualifier("serverHandler")
	ServerHandler serverHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {		
		ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(stringDecoder);
        pipeline.addLast(stringEncoder);
        pipeline.addLast(serverHandler);
        
	}
	
	@Bean(name = "stringEncoder")
	public StringEncoder stringEncoder() {
		return new StringEncoder(StandardCharsets.UTF_8);
	}

	@Bean(name = "stringDecoder")
	public StringDecoder stringDecoder() {
		return new StringDecoder(StandardCharsets.UTF_8);
	}

}