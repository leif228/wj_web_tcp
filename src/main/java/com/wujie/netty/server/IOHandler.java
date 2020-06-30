package com.wujie.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 *  netty服务器的监听 处理器
 *
 * @author flm 2017年10月27日
 */
@Slf4j
public class IOHandler extends ChannelInboundHandlerAdapter {

    //netty AttributeKey 相对于 web session【重要】
    public static final AttributeKey<String> KEY = AttributeKey.valueOf("IO");

//    private Producer producer;


//    public IOHandler(Producer producer){
//        this.producer=producer;
//    }

    /**
     * 读取数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("# 客户端 发送数据 ... :" + msg.toString());

//        DeviceSession session = ctx.channel().attr(KEY).get();     // 检测是否 自己注册的 客户端
//
//        ByteBuf buffer = (ByteBuf) msg;
//
//        if (buffer == null || session == null) {
//            closeConnection(ctx); // 关闭连接
//        }
//
//        MsgEntity msgEntity = new MsgEntity(buffer); // 解码  buffer 封装  msgEntity
//        log.info("# Accept Client data :" + msgEntity.toString());
//
//        if (MsgType.UNKNOW == msgEntity.getMsgType()) {
//            log.info("# 客户端 发送数据 类型未定义... :" + msgEntity.toString());
//            return;
//        }
//
//        if (!session.isActivity()) {
//            session.setActivity(true);
//            session.setImei(msgEntity.getImei());
//            SessionManager.getSingleton().addClient(session);
//        }
//        producer.putData(msgEntity);

    }


    /**
     * 客户端 注册
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);

        log.info(String.format("# client registered...：   %s ...", ctx.channel()));

//        DeviceSession session = new DeviceSession(ctx.channel());
//        // 绑定客户端到SOCKET
//        ctx.channel().attr(KEY).set(session);
    }


    /**
     * 客户端 失去连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        log.info(String.format("# client out... : %s", ctx.channel()));
//        DeviceSession session = ctx.channel().attr(KEY).getAndSet(null);
//
//        // 移除  session 并删除 该客户端
//        SessionManager.getSingleton().removeClient(session, true);
//
//        if (session.getDeviceID() != null) {
//            //  producer.onData(new Request(new RootMessage(MessageType.LOGOUT, null, null), session));
//        }

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
            }
        }

        super.userEventTriggered(ctx, evt);
    }


    /**
     * 报错 处理事件
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        log.error("# 客户端连接  Netty 出错...");
        cause.printStackTrace();
        //关闭连接
//        closeConnection(ctx);
    }
}