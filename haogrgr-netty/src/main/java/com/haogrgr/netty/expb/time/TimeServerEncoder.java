package com.haogrgr.netty.expb.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TimeServerEncoder extends MessageToByteEncoder<UnixTime> {

	@Override
	protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception {
		out.writeInt(msg.value());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
