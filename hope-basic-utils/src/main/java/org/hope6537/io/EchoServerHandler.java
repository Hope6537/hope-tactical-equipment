package org.hope6537.io;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//Annotate with @Sharable to share between channels
//在通道之间进行共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        //Write the received messages back .
        //Be aware that this will not flush the messages to the remote peer yet.
        System.out.println("Server received:"+msg);
        channelHandlerContext.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        //Flush all previous written messages (that are pending) to the remote peer, and close the channel after the operation is complete.
        channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        //Close channel on exception
        channelHandlerContext.close();
    }
}