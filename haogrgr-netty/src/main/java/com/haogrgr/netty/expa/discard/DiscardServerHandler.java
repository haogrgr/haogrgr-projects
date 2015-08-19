package com.haogrgr.netty.expa.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class DiscardServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		try {
			//			while (buf.isReadable()) {
			//				System.out.print((char) buf.readByte());
			//				System.out.flush();
			//			}

			System.out.print(buf.toString(io.netty.util.CharsetUtil.US_ASCII));
			System.out.flush();
		} finally {
			//ReferenceCountUtil.release(buf);
			buf.release();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
