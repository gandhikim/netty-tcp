package com.my.SampleGw;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component("NettyServer")
public class NettyServer {
	
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NettyServer.class);
	
	@Autowired
	@Qualifier("springConfig")
	private SpringConfig springConfig;
	
	private Channel serverChannel;
	    
    @PostConstruct
    public void start() throws Exception {
    	ServerBootstrap b = bootstrap(); 
    	serverChannel = b.bind(serverTcpSocketAddress())
    								.sync()
    								.channel()
    								.closeFuture()
    								.sync()
    								.channel();
    }
    
    @PreDestroy
	public void stop() {
		log.info("Shutdown server at " + serverTcpSocketAddress());
		if (serverChannel != null)
			serverChannel.close();
	}
    
    public ServerBootstrap bootstrap() {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(springConfig.getBossCount());
        EventLoopGroup workerGroup = new NioEventLoopGroup(springConfig.getWorkerCount());
        
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
		          @Override
		          protected void initChannel(SocketChannel ch) throws Exception {
		            ChannelPipeline pipeline = ch.pipeline();
		            //pipeline.addLast(SERVICE_HANDLER);
		            
		          }
		        }
		);

		Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
		Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
		for (ChannelOption option : keySet) {
			b.option(option, tcpChannelOptions.get(option));
		}
		return b;
	}
    
    public Map<ChannelOption<?>, Object> tcpChannelOptions() {
		Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
		options.put(ChannelOption.SO_KEEPALIVE, springConfig.isKeepAlive());
		options.put(ChannelOption.SO_BACKLOG, springConfig.getBacklog());
		options.put(ChannelOption.SO_LINGER, springConfig.getLinger());
		options.put(ChannelOption.TCP_NODELAY, springConfig.isNodelay());
		options.put(ChannelOption.SO_RCVBUF, springConfig.getRcvBuf());
		options.put(ChannelOption.SO_SNDBUF, springConfig.getSndBuf());
		return options;
	}

    public InetSocketAddress serverTcpSocketAddress() {
		if ("org".equals(springConfig.getDemonType())) {
			return new InetSocketAddress(springConfig.getOrgServerPort());
		}
		return null;
	}

}
