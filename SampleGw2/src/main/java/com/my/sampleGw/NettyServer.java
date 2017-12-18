package com.my.sampleGw;

import java.net.InetSocketAddress;

import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
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
import io.netty.handler.logging.LoggingHandler;

/**
 * add jvm [-Dio.netty.noResourceLeakDetection]
 *  
 * @author gandhi.kim
 * */

@Component("NettyServer")
public class NettyServer {
	
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NettyServer.class);
	
	@Autowired
	@Qualifier("springConfig")
	private SpringConfig springConfig;
	
	@Autowired
	@Qualifier("stringDecoder")
	public StringDecoder stringDecoder;
	
	@Autowired
	@Qualifier("stringEncoder")
	public StringEncoder stringEncoder;
	
	@Autowired
	@Qualifier("serverChannelInitializer")
	private ServerChannelInitializer serverChannelInitializer;
	
	@Autowired
	@Qualifier("bossGroup")
	private EventLoopGroup bossGroup;
	
	@Autowired
	@Qualifier("workerGroup")
	private EventLoopGroup workerGroup;
	
	@Autowired
	@Qualifier("serverBootstrap")
	private ServerBootstrap b;
	
	@Autowired
	@Qualifier("inetSocketAddress")
	private InetSocketAddress inetSocketAddress;
	
	private Channel serverChannel;
	
    @PostConstruct
    public void start() throws Exception {
    	try {
    		serverChannel = b.bind(inetSocketAddress)
    				.sync()
    				.channel()
    				.closeFuture()
    				.sync()
    				.channel();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bossGroup != null)
				bossGroup.shutdownGracefully();
			
			if (workerGroup != null)
				workerGroup.shutdownGracefully();
			
			if (serverChannel != null)
				serverChannel.close();
		}
	
    }
    
    @PreDestroy
	public void stop() {
		if (bossGroup != null)
			bossGroup.shutdownGracefully();
		
		if (workerGroup != null)
			workerGroup.shutdownGracefully();

		if (serverChannel != null)
			serverChannel.close();
		
	}

    class ServerHandler 
    		extends SimpleChannelInboundHandler<Object> {
    	
    	@Override
    	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    		String respMsg = "[echo]" + msg;
    		ctx.channel().writeAndFlush( respMsg ).addListener(ChannelFutureListener.CLOSE);
    		log.info("channelRead - msg : " + (String)respMsg);
    	}
    	
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

		}
    }

	@Bean(name = "stringEncoder")
	public StringEncoder stringEncoder() {
		return new StringEncoder(StandardCharsets.UTF_8);
	}

	@Bean(name = "stringDecoder")
	public StringDecoder stringDecoder() {
		return new StringDecoder(StandardCharsets.UTF_8);
	}

    @Component
    @Qualifier("serverChannelInitializer")
    class ServerChannelInitializer
    		extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {		
			ChannelPipeline pipeline = ch.pipeline();

            pipeline.addLast(stringDecoder);
            pipeline.addLast(stringEncoder);
            pipeline.addLast(new ServerHandler());
            
		}
    }
    
    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
	private NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(springConfig.getBossCount());
	}
    
    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    private NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(springConfig.getWorkerCount());
	}
    
    @Bean(name = "serverBootstrap")
    private ServerBootstrap setBootstrap() {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler())
			.childHandler(serverChannelInitializer);

		b.option(ChannelOption.SO_KEEPALIVE, 	springConfig.isKeepAlive()); 
		b.option(ChannelOption.SO_BACKLOG, 		springConfig.getBacklog());    
		b.option(ChannelOption.SO_LINGER, 		springConfig.getLinger());      
		b.option(ChannelOption.TCP_NODELAY, 	springConfig.isNodelay());    
		b.option(ChannelOption.SO_RCVBUF, 		springConfig.getRcvBuf());      
		b.option(ChannelOption.SO_SNDBUF, 		springConfig.getSndBuf());      
		
		return b;
	}
	
    @Bean(name = "inetSocketAddress")
    private InetSocketAddress serverTcpSocketAddress() {
		if ("org".equals(springConfig.getDemonType())) {
			log.info("org port : " + springConfig.getOrgServerPort());
			return new InetSocketAddress(springConfig.getOrgServerPort());
		}
		return null;
	}
    
}
