package com.wujie.netty.server.business;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wujie.netty.pojo.Device;
import com.wujie.netty.protocol.TcpProtocol;
import com.wujie.netty.utils.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class OutBusinessHandler extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        log.debug("OutBusinessHandler#encode 服务端 发送数据 ... :" + ctx.channel() + "==>数据：" + msg.toString());
        if (msg instanceof TcpProtocol){
            TcpProtocol protocol = (TcpProtocol) msg;
            out.writeByte(protocol.getHeader());
            out.writeInt(protocol.getLen());
            out.writeBytes(protocol.getData());
            out.writeByte(protocol.getTail());
            log.debug("数据编码成功："+out);
        }else {
            log.info("不支持的数据协议："+msg.getClass()+"\t期待的数据协议类是："+TcpProtocol.class);
        }
    }
}
