package com.my.sampleGw.util;

//import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
//import java.nio.charset.Charset;


public class TCPSocketClient {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( TCPSocketClient.class );
	public String resultData;
	
	public String send( String address, int port, String message, int timeout ) throws Exception {
		
		String responseMessage = null;
		SocketChannel socket = null;
		boolean isException = false;
		//Mutex로 사용할 Object 변수를 선언합니다.
        Object lock = new Object();
        
        synchronized (lock) {
        	
        	try {
    			SocketAddress socketAddress = new InetSocketAddress(address, port);
    			socket = SocketChannel.open(socketAddress);
    			socket.configureBlocking(true);
    			socket.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
    			socket.setOption(StandardSocketOptions.SO_RCVBUF, 4098);
    			socket.setOption(StandardSocketOptions.SO_SNDBUF, message.getBytes("UTF-8").length);
    			//SO_LINGER:타임아웃 값을 설정(기본값 음수, 즉 비활성화), 블로킹 모드에서 close() 사용해서 중단할 때 전송할 Data가 남아 있으면 주어진 linger interval 만큼 대기 한다.
    			socket.setOption(StandardSocketOptions.SO_LINGER, 0);
    			socket.connect(socketAddress);
    			
    			
    			byte[] bytes = message.getBytes("UTF-8");
    			ByteBuffer wbuf = ByteBuffer.allocate(bytes.length);
    			wbuf.put(bytes);
    			wbuf.clear();
    			socket.write(wbuf);
    			wbuf.clear();
    			
    			
    			ByteBuffer rbuf = ByteBuffer.allocate(4098);
    			socket.read(rbuf);
    			responseMessage = new String(rbuf.array(), "UTF-8");
    			responseMessage = responseMessage.trim();
    			rbuf.clear();
    			
    		}
    		catch( SocketTimeoutException socketEx )
    		{
    			log.error( "TCPSocketClient SocketTimeoutException", socketEx );
    			isException = true;
    			throw socketEx;
    		}
    		catch( ConnectException connectEx )
    		{
    			log.error( "TCPSocketClient ConnectException", connectEx ); 
    			throw connectEx;
    		}
    		catch( SocketException socketEx )
    		{
    			log.error( "TCPSocketClient SocketException", socketEx );
    			isException = true;
    			throw socketEx;
    		}
    		catch( Exception e )
    		{
    			log.error( "TCPSocketClient Exception", e );
    			throw e;
    		}
    		finally
    		{
    			if ( socket != null )
    				socket.close();
    			
    			if("Message Cracked".equals(responseMessage) || isException == true) {
    				for(int i = 0; i < 3; i++) {
    					Thread.sleep(1000);
    					
    					responseMessage = retrySend(address, port, message );
    					
    					if(!"Message Cracked".equals(responseMessage)){
    						return responseMessage;
    					}
    				}
    			}
    			
    		}
		}
	
		return responseMessage;
	}
	
	public String send( String address, int port, String message, int timeout, String charSet ) throws Exception
	{
		String responseMessage = null;
		Socket socket = null;
		PrintWriter oos = null;
		//BufferedInputStream ois = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		boolean isException = false;
		//it declare that Object Variables use into Mutex
        Object lock = new Object();
		
        synchronized (lock) {
        	try
    		{
    			SocketAddress socketAddress = new InetSocketAddress(address, port);
    			socket = new Socket();
    			socket.setReceiveBufferSize(4098);
    			socket.setSendBufferSize(message.length());
    			socket.setKeepAlive(true);
    			socket.setSoLinger(true, 0);
    			socket.connect(socketAddress, 30000); /* socket connection timeout */
    			socket.setSoTimeout(timeout); /* socket read timeout */
    			
				oos = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), charSet ) );
				oos.write(message);
				oos.flush();
				
    			/*while(oos.checkError()){
    				log.info( "Client Request checkError : " + oos.checkError() );
    				oos.write( message + "\r\n" );
    				oos.flush();
    			}*/
				
				
				isr = new InputStreamReader(socket.getInputStream(), charSet);
				br = new BufferedReader(isr);
				String str = "";
				responseMessage = "";
    			/*int count = 0;
    			while(!br.ready()){
    				//15초 loop out
    				if(count >= 150)
    					break;
    				Thread.sleep(100);
    				log.info( "Client Response ready : " + count + " " + br.ready() );
    				count++;
    			}*/
    					
				while ((str = br.readLine()) != null) {
					responseMessage += str;
				}
    					
    			/*while(br.ready()){
    				while ((str = br.readLine()) != null) {
    					responseMessage += str;
    				}
    			}*/
    					
    					
    			/*ois = new BufferedInputStream( socket.getInputStream() );
    			byte[] buff = new byte[ 8192 ];
    			ois.read( buff );
    			responseMessage += "\r\n";
    			responseMessage = new String( buff, "UTF-8" ).trim();*/

    		}
    		catch( SocketTimeoutException socketEx )
    		{
    			log.error( "TCPSocketClient SocketTimeoutException", socketEx );
    			throw socketEx;
    		}
    		catch( ConnectException connectEx )
    		{
    			log.error( "TCPSocketClient ConnectException", connectEx );
    			//isException = true;
    			throw connectEx;
    		}
    		catch( SocketException socketEx )
    		{
    			log.error( "TCPSocketClient SocketException", socketEx );
    			throw socketEx;
    		}
    		catch( Exception e )
    		{
    			log.error( "TCPSocketClient Exception", e );
    			//isException = true;
    			throw e;
    		}
    		finally
    		{
    			if( isr != null )
    				isr.close();
    			if(br != null)
    				br.close();
    			/*if ( ois != null )
    				ois.close();*/
    			if ( oos != null )
    				oos.close();
    			if ( socket != null )
    				socket.close();
    			
    			if("Message Cracked".equals(responseMessage) || isException == true){
    				for(int i = 0; i < 3; i++){
    					Thread.sleep(1000);
    				
    					responseMessage = retrySend(address, port, message );
    					
    					if(!"Message Cracked.".equals(responseMessage)){
    						return responseMessage;
    					}
    				}
    			}
    		}
		}
	
		return responseMessage;
	}
	
	public String retrySend( String address, int port, String message ) throws Exception
	{
		String responseMessage = null;
		Socket socket = null;
		PrintWriter oos = null;
		OutputStreamWriter osr = null;
		//BufferedInputStream ois = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		boolean isException = false;
		//it declare that Object Variables use into Mutex
        Object lock = new Object();
        synchronized (lock) {
			try{
			
				SocketAddress socketAddress = new InetSocketAddress(address, port);
				socket = new Socket();
				socket.setReceiveBufferSize(4098);
				socket.setSendBufferSize(message.length());
				socket.setKeepAlive(true);
				socket.setSoLinger(true, 0);
				socket.connect(socketAddress, 30000); /* socket connection timeout */
				//socket = new Socket( address, port );
				//socket.setKeepAlive(true);
			
				osr = new OutputStreamWriter( socket.getOutputStream(), "UTF-8" );
				oos = new PrintWriter( osr );
				oos.write( message);
				oos.flush();
				/*while(oos.checkError()){
					log.info( "Client Request checkError : " + oos.checkError() );
					oos.close();
					osr = new OutputStreamWriter( socket.getOutputStream(), "UTF-8" );
					oos = new PrintWriter( osr );
					oos.write( message + "\r\n" );
				}
				oos.flush();*/
				
				isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
				br = new BufferedReader(isr);
				String str = "";
				responseMessage = "";
				
				/*int count = 0;
				while(!br.ready()){
					//15초 loop out
					if(count >= 300)
						break;
					Thread.sleep(100);
					log.info( "Client Response ready : " + count + " " + br.ready() );
					count++;
				}*/
				while ((str = br.readLine()) != null) {
					responseMessage += str;
				}
			    
				/*ois = new BufferedInputStream( socket.getInputStream() );
				byte[] buff = new byte[ 8192 ];
				ois.read( buff );
				responseMessage += "\r\n";
				responseMessage = new String( buff, "UTF-8" ).trim();*/
				//responseMessage = new String( buff, "UTF-8" ).trim();

			}
			catch(SocketTimeoutException socketEx )
			{
				log.error( "TCPSocketClient Retry SocketTimeoutException", socketEx );
				//throw socketEx;
				isException = true;
			}
			catch( ConnectException connectEx )
			{
				log.error( "TCPSocketClient Retry ConnectException", connectEx );
				//throw connectEx;
				isException = true;
			}
			catch( Exception e )
			{
				log.error( "TCPSocketClient Retry Exception", e );
				//throw e;
				isException = true;
			}
			finally
			{
				if( isr != null )
					isr.close();
				if(br != null)
					br.close();
				/*if ( ois != null )
					ois.close();*/
				if ( oos != null )
					oos.close();
				if ( socket != null )
					socket.close();
				
				if(isException == true){
					return "Message Cracked.";
				}
			}
        }
        
		return responseMessage;
	}
}
