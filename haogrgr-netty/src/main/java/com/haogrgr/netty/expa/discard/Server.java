package com.haogrgr.netty.expa.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	public static void main(String[] args) throws Exception {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup work = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(boss, work);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new DiscardServerHandler());
				}
			});
			bootstrap.option(ChannelOption.SO_BACKLOG, 128);
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture start = bootstrap.bind(1998).sync();

			start.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
	}

}
