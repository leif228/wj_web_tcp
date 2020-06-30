package com.wujie.netty.client.decoder;

import com.wujie.netty.server.ChannelManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DecoderHandler extends ByteToMessageDecoder {
    //最小的数据长度：开头标准位1字节
    private static int MIN_DATA_LEN=6;
    //数据解码协议的开始标志
    private static byte PROTOCOL_HEADER=0x58;
    //数据解码协议的结束标志
    private static byte PROTOCOL_TAIL=0x63;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("DecoderHandler  decode收到数据长度："+in.readableBytes());
        if (in.readableBytes()>MIN_DATA_LEN){
            log.info("开始解码数据……");
            //标记读操作的指针
            in.markReaderIndex();
            byte header=in.readByte();
            if (header==PROTOCOL_HEADER){
                log.info("数据开头格式正确");
                //读取字节数据的长度
                int len=in.readInt();
                //数据可读长度必须要大于len，因为结尾还有一字节的解释标志位
                if (len>=in.readableBytes()){
                    log.info(String.format("数据长度不够，数据协议len长度为：%1$d,数据包实际可读内容为：%2$d正在等待处理拆包……",len,in.readableBytes()));
                    in.resetReaderIndex();
                    /*
                     **结束解码，这种情况说明数据没有到齐，在父类ByteToMessageDecoder的callDecode中会对out和in进行判断
                     * 如果in里面还有可读内容即in.isReadable为true,cumulation中的内容会进行保留，，直到下一次数据到来，将两帧的数据合并起来，再解码。
                     * 以此解决拆包问题
                     */
                    return;
                }
                byte [] data=new byte[len];
                in.readBytes(data);//读取核心的数据
                byte tail=in.readByte();
                if (tail==PROTOCOL_TAIL){
                    log.info("数据解码成功");
                    out.add(data);
                    //如果out有值，且in仍然可读，将继续调用decode方法再次解码in中的内容，以此解决粘包问题
                }else {
                    log.info(String.format("数据解码协议结束标志位:%1$d [错误!]，期待的结束标志位是：%2$d",tail,PROTOCOL_TAIL));
                    return;
                }
            }else {
                log.info("开头不对，可能不是期待的客服端发送的数，将自动略过这一个字节");
            }
        }else {
            log.info("数据长度不符合要求，期待最小长度是："+MIN_DATA_LEN+" 字节");
            return;
        }

    }

    /**
     * 客户端 注册
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);


        log.info(String.format("DecoderHandler# # client registered...：   %s ...", ctx.channel()));

//        DeviceSession session = new DeviceSession(ctx.channel());
//        // 绑定客户端到SOCKET
//        ctx.channel().attr(KEY).set(session);
    }

    /**
     * 报错 处理事件
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        log.info("DecoderHandler# # 客户端连接  Netty 出错...");
//        cause.printStackTrace();
        //关闭连接
//        closeConnection(ctx);
    }
    /**
     * 心跳机制  用户事件触发
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;

            //检测 是否 这段时间没有和服务器联系
            if (e.state() == IdleState.ALL_IDLE) {
                //检测心跳
//                checkIdle(ctx);
                log.info(String.format("DecoderHandler# # client userEventTriggered... : %s", ctx.channel()));
            }
        }

        super.userEventTriggered(ctx, evt);
    }


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        log.info("DecoderHandler# 客户端 channelWritabilityChanged ... :" + ctx.channel() );
    }
    /**
     * 读取完毕客户端发送过来的数据之后的操作
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("DecoderHandler# 服务端接收数据完毕.."+ctx.channel());
    }
    /**
     * 客户端 失去连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        log.info(String.format("DecoderHandler# # client out... : %s", ctx.channel()));

        Channel incoming = ctx.channel();
        if(incoming.hasAttr(ChannelManager.deviceInfoVoAttr)){
            ChannelManager.deviceChannels.remove(incoming.attr(ChannelManager.deviceInfoVoAttr).get().getUniqueNo());
        }

//        DeviceSession session = ctx.channel().attr(KEY).getAndSet(null);
//
//        // 移除  session 并删除 该客户端
//        SessionManager.getSingleton().removeClient(session, true);
//
//        if (session.getDeviceID() != null) {
//            //  producer.onData(new Request(new RootMessage(MessageType.LOGOUT, null, null), session));
//        }

    }
}
