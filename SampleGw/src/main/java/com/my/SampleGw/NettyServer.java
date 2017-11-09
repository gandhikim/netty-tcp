package com.my.SampleGw;

import java.net.InetSocketAddress;

import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

@Component("NettyServer")
public class NettyServer {
	
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NettyServer.class);
	
	@Autowired
	@Qualifier("springConfig")
	private SpringConfig springConfig;
	
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
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
		
    	log.info("stop()");

		if (bossGroup != null)
			bossGroup.shutdownGracefully();
		
		if (workerGroup != null)
			workerGroup.shutdownGracefully();
		
		if (serverChannel != null)
			serverChannel.close();
		
		
	}
    
    private ServerBootstrap bootstrap() {
		
		bossGroup = new NioEventLoopGroup(springConfig.getBossCount());
        workerGroup = new NioEventLoopGroup(springConfig.getWorkerCount());
        
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
		          @Override
		          protected void initChannel(SocketChannel ch) throws Exception {
		            ChannelPipeline pipeline = ch.pipeline();
		            
		            pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
		            pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
		            pipeline.addLast(new ServerHandler());
		           
		          }
		        }
		);

		b.option(ChannelOption.SO_KEEPALIVE, 	springConfig.isKeepAlive()); 
		b.option(ChannelOption.SO_BACKLOG, 		springConfig.getBacklog());    
		b.option(ChannelOption.SO_LINGER, 		springConfig.getLinger());      
		b.option(ChannelOption.TCP_NODELAY, 	springConfig.isNodelay());    
		b.option(ChannelOption.SO_RCVBUF, 		springConfig.getRcvBuf());      
		b.option(ChannelOption.SO_SNDBUF, 		springConfig.getSndBuf());      
		
		return b;
	}

    private InetSocketAddress serverTcpSocketAddress() {
		if ("org".equals(springConfig.getDemonType())) {
			log.info("org port : " + springConfig.getOrgServerPort());
			return new InetSocketAddress(springConfig.getOrgServerPort());
		}
		return null;
	}
    
    class ServerHandler extends SimpleChannelInboundHandler<Object> {
    	@Override
    	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    		// echo server
    		ctx.channel().writeAndFlush( msg ).addListener(ChannelFutureListener.CLOSE);
    		log.info("channelRead - msg : " + (String)msg);
    	}
    	
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

		}
    	
    }

}
