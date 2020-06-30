package com.wujie.netty.server.decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wujie.app.business.util.NumConvertUtil;
import com.wujie.netty.pojo.Device;
import com.wujie.netty.protocol.WjProtocol;
import com.wujie.netty.server.ChannelManager;
import com.wujie.netty.utils.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
public class WjDecoderHandler extends ByteToMessageDecoder {
    //最小的数据长度：开头标准位1字节
    private static int MIN_DATA_LEN=21;
    //数据解码协议的开始标志
    private static String PROTOCOL_HEADER="$TCUB&";

    private static String FORMAT_TX="TX";
    private static String FORMAT_JS="JS";
    private static String FORMAT_AT="AT";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.debug("DecoderHandler  decode收到数据长度："+in.readableBytes());
        int totalLen = in.readableBytes();
        if (in.readableBytes()>=MIN_DATA_LEN){
            log.debug("开始解码数据……");
            WjProtocol wjProtocol = new WjProtocol();
            //标记读操作的指针
            in.markReaderIndex();
            byte[] headerbyte = new byte[6];//##6
            in.readBytes(headerbyte);
            String headerStr = new String(headerbyte);
            wjProtocol.setHeader(headerStr);

            if (PROTOCOL_HEADER.equals(headerStr)){
                log.debug("数据开头格式正确");
                //读取字节数据的长度
                short lenShort = in.readShort();//##2
                wjProtocol.setLen(lenShort);
                int len= lenShort;
                int subHeaderLen = len - 8;
                //数据可读长度必须要大于len，因为结尾还有一字节的解释标志位
                int rr = in.readableBytes();
                if (subHeaderLen>=in.readableBytes()){
                    log.debug(String.format("数据长度不够，数据协议len长度为：%1$d,数据包实际可读内容为：%2$d正在等待处理拆包……",len,in.readableBytes()));
                    in.resetReaderIndex();
                    /*
                    **结束解码，这种情况说明数据没有到齐，在父类ByteToMessageDecoder的callDecode中会对out和in进行判断
                    * 如果in里面还有可读内容即in.isReadable为true,cumulation中的内容会进行保留，，直到下一次数据到来，将两帧的数据合并起来，再解码。
                    * 以此解决拆包问题
                     */
                    return;
                }
                char verChar = in.readChar();//##1
                wjProtocol.setVer(verChar);
                char encryptChar = in.readChar();//##1
                wjProtocol.setEncrypt(encryptChar);
                short platShort = in.readShort();//##2
                wjProtocol.setPlat(platShort);
                short maincmdShort = in.readShort();//##2
                wjProtocol.setMaincmd(maincmdShort);
                short subcmdShort = in.readShort();//##2
                wjProtocol.setSubcmd(subcmdShort);

                byte[] formatbyte = new byte[2];//##2
                in.readBytes(formatbyte);
                String format = new String(formatbyte);
                wjProtocol.setFormat(format);

                short backShort = in.readShort();//##2
                wjProtocol.setBack(backShort);

                int dataLen = len - MIN_DATA_LEN;
                if(dataLen > 0){
                    byte [] data=new byte[dataLen];
                    in.readBytes(data);//读取核心的数据##n
                    wjProtocol.setUserdata(data);
                }

                char checkSumChar = in.readChar();//##1
                wjProtocol.setCheckSum(checkSumChar);

                doProtocol(ctx,wjProtocol);
            }else {
                log.debug("开头不对，可能不是期待的客服端发送的数，将自动略过这一个字节");
                return;
            }
        }else {
            log.debug("数据长度不符合要求，期待最小长度是："+MIN_DATA_LEN+" 字节");
            return;
        }

    }

    private void doProtocol(ChannelHandlerContext ctx, WjProtocol wjProtocol) {
        JSONObject objParam = null;
        String tx = "";

        if(FORMAT_TX.equals(wjProtocol.getFormat())){
            String dataStr = new String(wjProtocol.getUserdata());
            tx = dataStr;
        }else if(FORMAT_JS.equals(wjProtocol.getFormat())){
            String jsonStr = new String(wjProtocol.getUserdata());
            log.debug(jsonStr);
            objParam = JSONObject.parseObject(jsonStr);
        }else if(FORMAT_AT.equals(wjProtocol.getFormat())){
            String atStr = new String(wjProtocol.getUserdata());
        }

        //======业务处理======
        if(wjProtocol.getMaincmd() == 0 && wjProtocol.getSubcmd() == 1){//终端→服务 登录
            this.nettyLogin(ctx,objParam);
        }
        if(wjProtocol.getMaincmd() == 0 && wjProtocol.getSubcmd() == 0){//终端→服务 心跳
            this.nettyReq(ctx,tx);
        }
    }

    private void nettyLogin(ChannelHandlerContext ctx, JSONObject objParam) {
        if(ObjectUtils.isEmpty(objParam)){
            ctx.channel().close();
        }
//        JSONObject jsonObject = JSONObject.parseObject(data.toString());
        Object userId1 = objParam.get("oid");
//        String oid = JSON.parseObject(userId1.toString(), String.class);
        String oid = (String) userId1;
//        UserInfoVo userInfoVo = appUserService.getUserInfoById(userId);
        Device device = new Device(oid,System.currentTimeMillis()+"");
        if (ChannelManager.deviceChannels.containsKey(device.getUniqueNo())){
            Channel channel = ChannelManager.deviceChannels.get(device.getUniqueNo());
            // TODO 需要定义返回的JSON格式，通知用户被挤下去了
            channel.close();
        }
        ctx.channel().attr(ChannelManager.deviceInfoVoAttr).set(device);
        ChannelManager.deviceChannels.put(device.getUniqueNo(), ctx.channel());
    }
    private void nettyReq(ChannelHandlerContext ctx, String tx) {
        log.info("心跳nettyReq" + tx);
    }
    /**
     * 客户端 注册
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);


        log.debug(String.format("DecoderHandler# # client registered...：   %s ...", ctx.channel()));

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

        log.debug("DecoderHandler# # 客户端连接  Netty 出错...");
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
                log.debug(String.format("DecoderHandler# # client userEventTriggered... : %s", ctx.channel()));
            }
        }

        super.userEventTriggered(ctx, evt);
    }


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        log.debug("DecoderHandler# 客户端 channelWritabilityChanged ... :" + ctx.channel() );
    }
    /**
     * 读取完毕客户端发送过来的数据之后的操作
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.debug("DecoderHandler# 服务端接收数据完毕.."+ctx.channel());
    }
    /**
     * 客户端 失去连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        log.debug(String.format("DecoderHandler# # client out... : %s", ctx.channel()));

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
