package com.haogrgr.netty.expb.time;

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
					//这个例子中, 按照正常的思维, 应该是Handler在前面, Encoder在后面, 
					//但是, 如果Handler在前面, 则在Handler中使用ctx.writeAndFlush等方法时, 是不会经过Encoder的, 
					//因为ctx上的writeAndFlush等方法是从当前handler往前查找handler处理, 而Encoder在handler后面.
					//如果是使用ctx.channel().writeAndFlush时, 就会从头开始走完整的pipline.
					ch.pipeline().addLast(new TimeServerHandler());
					ch.pipeline().addLast(new TimeServerEncoder());
				}
			});
			bootstrap.option(ChannelOption.SO_BACKLOG, 128);
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture start = bootstrap.bind(8080).sync();

			start.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
	}

}
