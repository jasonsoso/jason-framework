package com.jason.framework.netty.object;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;

public class ObjectServer {
	public static void main(String args[]) {
		// Server服务启动器
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		// 设置一个处理客户端消息和各种消息事件的类(Handler)
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				//先编码 --> 后处理自己的业务
				return Channels.pipeline(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())), new ObjectServerHandler());
			}
		});
		bootstrap.bind(new InetSocketAddress(8003));
	}
}

class ObjectServerHandler extends SimpleChannelHandler {
	/**
	 * 当接受到消息的时候触发
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Command command = (Command) e.getMessage();
		// 打印看看是不是我们刚才传过来的那个
		System.out.println(command.getActionName());
	}
}
