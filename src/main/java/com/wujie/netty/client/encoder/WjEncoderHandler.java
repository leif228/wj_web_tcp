package com.wujie.netty.client.encoder;

import com.wujie.netty.protocol.TcpProtocol;
import com.wujie.netty.protocol.WjProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WjEncoderHandler extends MessageToByteEncoder {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        log.info("OutBusinessHandler#encode 服务端 发送数据 ... :" + ctx.channel() + "==>数据：" + msg.toString());
        if (msg instanceof WjProtocol){
            WjProtocol protocol = (WjProtocol) msg;
            out.writeBytes(protocol.getHeader().getBytes());
            out.writeShort(protocol.getLen());
            out.writeChar(protocol.getVer());
            out.writeChar(protocol.getEncrypt());
            out.writeShort(protocol.getPlat());
            out.writeShort(protocol.getMaincmd());
            out.writeShort(protocol.getSubcmd());
            out.writeBytes(protocol.getFormat().getBytes());
            out.writeShort(protocol.getBack());
            out.writeBytes(protocol.getUserdata());
            out.writeChar(protocol.getCheckSum());

            log.info("数据编码成功："+out.readableBytes());
        }else {
            log.info("不支持的数据协议："+msg.getClass()+"\t期待的数据协议类是："+TcpProtocol.class);
        }
    }


}
