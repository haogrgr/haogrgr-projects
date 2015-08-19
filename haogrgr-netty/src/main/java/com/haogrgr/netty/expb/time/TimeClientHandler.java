package com.haogrgr.netty.expb.time;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter implements ChannelHandler {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		UnixTime m = (UnixTime) msg;
		System.out.println(m);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
