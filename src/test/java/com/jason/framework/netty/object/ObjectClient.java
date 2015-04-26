package com.jason.framework.netty.object;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

public class ObjectClient {

	public static void main(String args[]) {
		ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectEncoder(), new ObjectClientHandler());
			}
		});

		bootstrap.connect(new InetSocketAddress("127.0.0.1", 8003));
	}
}

class ObjectClientHandler extends SimpleChannelHandler {
	/**
	 * 当绑定到服务端的时候触发，给服务端发消息。
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		// 向服务端发送Object信息
		sendObject(e.getChannel());
	}
	/**
	 * 发送Object
	 * @param channel
	 */
	private void sendObject(Channel channel) {
		Command command = new Command();
		command.setActionName("Hello action.");
		channel.write(command);
	}
}
