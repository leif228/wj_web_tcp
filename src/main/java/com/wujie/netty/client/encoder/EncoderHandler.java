package com.wujie.netty.client.encoder;

import com.wujie.netty.protocol.TcpProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncoderHandler extends MessageToByteEncoder {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        log.info("OutBusinessHandler#encode 服务端 发送数据 ... :" + ctx.channel() + "==>数据：" + msg.toString());
        if (msg instanceof TcpProtocol){
            TcpProtocol protocol = (TcpProtocol) msg;
            out.writeByte(protocol.getHeader());
            out.writeInt(protocol.getLen());
            out.writeBytes(protocol.getData());
            out.writeByte(protocol.getTail());
            log.info("数据编码成功："+out);
        }else {
            log.info("不支持的数据协议："+msg.getClass()+"\t期待的数据协议类是："+TcpProtocol.class);
        }
    }


}
