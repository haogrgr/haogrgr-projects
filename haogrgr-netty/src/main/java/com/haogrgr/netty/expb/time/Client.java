package com.haogrgr.netty.expb.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	public static void main(String[] args) throws Exception {
		EventLoopGroup work = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(work);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TimeClientDecoder(), new TimeClientHandler());
				}
			});

			ChannelFuture start = bootstrap.connect("127.0.0.1", 8080).sync();

			start.channel().closeFuture().sync();
		} finally {
			work.shutdownGracefully();
		}
	}

}
