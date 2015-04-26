package com.jason.framework.netty.message;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class MessageServer {

	public static void main(String args[]){
		//服务启动器
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool()));
		//设置一个处理客户端消息和各种消息事件的类(Handler)
		bootstrap.setPipelineFactory(new ChannelPipelineFactory(){
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new BusinessHandler());
			}			
		});
		//开放8000端口供客户端连接
		bootstrap.bind(new InetSocketAddress(8002));
	}
	
	private static class BusinessHandler extends SimpleChannelHandler{
		// 服务端收到客户端发送过来的消息时，触发此方法
		@Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        	ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
        	System.out.println("Receive:"+buffer.toString(Charset.defaultCharset()));
        	String msg = buffer.toString(Charset.defaultCharset()) + "has been processed!";
        	ChannelBuffer buffer2 = ChannelBuffers.buffer(msg.length());
        	buffer2.writeBytes(msg.getBytes());
        	e.getChannel().write(buffer2);
        }
	}
	
}
