package com.wujie.netty.server.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public class EncoderHandler extends ChannelOutboundHandlerAdapter {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg,
                      ChannelPromise promise) throws Exception {
        log.debug("EncoderHandler#write 服务端 发送数据 ... :" + ctx.channel() + "==>数据：" + msg.toString());
//        if (msg instanceof byte[]) {
//            byte[] bytesWrite = (byte[])msg;
//            ByteBuf buf = ctx.alloc().buffer(bytesWrite.length);
////            logger.info("向设备下发的信息为："+TCPServerNetty.bytesToHexString(bytesWrite));
//
//            buf.writeBytes(bytesWrite);
        ctx.writeAndFlush(msg).addListener(new ChannelFutureListener(){
            @Override
            public void operationComplete(ChannelFuture future)
                    throws Exception {
                log.debug("下发成功！");
            }
        });
//        }
    }



}
