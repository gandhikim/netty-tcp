package com.my.SampleGw;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


@Component("NettyClient")
public class NettyClient {

	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NettyClient.class);
	public String resultData;

	@Autowired
	@Qualifier("springConfig")
	private SpringConfig springConfig;

	public String send(String address, int port, String message, int timeout) throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		final NettyClient client = this;
		final String msg = message;
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")),
							new StringEncoder(Charset.forName("UTF-8")), new ClientHandler(msg, client));
				}
			});

			// Start the client.
			ChannelFuture f = b.connect(address, port).sync();
			if (f.isSuccess()) {

			}
			// Wait until the connection is closed.
			f.channel().closeFuture().sync();

			log.info("RESULT_DATE : " + resultData);

		} finally {
			workerGroup.shutdownGracefully();
		}

		return resultData;
	}
	
	class ClientHandler 
			extends SimpleChannelInboundHandler<Object> {

		String reqestMessage;
		NettyClient client;
		
		public ClientHandler(String message, NettyClient obj){
			reqestMessage = message;
			client = obj;
		}
		
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			log.info( "Client Request : " + reqestMessage );
			byte[] bytes = reqestMessage.getBytes("UTF-8");
			ByteBuf sendBuf = ctx.alloc().buffer(bytes.length);
			sendBuf.writeBytes(bytes);
			ChannelFuture f = ctx.writeAndFlush(sendBuf);
		}
		
	    @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    	reqestMessage = msg.toString();
	    }
	    
	    @Override
	    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			log.info( "Client Response : " + reqestMessage );
			client.resultData = reqestMessage;
			ctx.close();
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        cause.printStackTrace();
	        ctx.close();
	    }

		@Override
		protected void channelRead0(ChannelHandlerContext arg0, Object arg1) throws Exception {
			// TODO Auto-generated method stub
			
		}
	    
	}
	
	

}
