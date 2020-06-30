package com.wujie.netty.client.business;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wujie.netty.dataTransefer.DTObject;
import com.wujie.netty.pojo.Device;
import com.wujie.netty.pojo.User;
import com.wujie.netty.protocol.TcpProtocol;
import com.wujie.netty.server.ChannelManager;
import com.wujie.netty.utils.ByteUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.UUID;

@Slf4j
public class InBusinessHandler extends ChannelInboundHandlerAdapter {
    private ObjectMapper objectMapper= ByteUtils.InstanceObjectMapper();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof byte []){
            log.info("解码后的字节码："+new String((byte[]) msg,"UTF-8"));
            try {
                Object objectContainer = objectMapper.readValue((byte[]) msg, DTObject.class);
                if (objectContainer instanceof DTObject){
                    DTObject data = (DTObject) objectContainer;
                    if (data.getClassName()!=null&&data.getObject().length>0){
                        Object object = objectMapper.readValue(data.getObject(), Class.forName(data.getClassName()));
                        log.info("收到实体对象："+object);
                        //测试
                        //this.sendToService(ctx);
                    }
                }
            }catch (Exception e){
                log.info("对象反序列化出现问题："+e);
            }

        }
    }

    private void sendToService(ChannelHandlerContext ctx) {
        try {
            User user = new User();
            user.setBirthday(new Date());
            user.setUID(UUID.randomUUID().toString());
            user.setName("冉鹏峰");
            user.setAge(24);
            DTObject dtObject = new DTObject();
            dtObject.setClassName(user.getClass().getName());
            dtObject.setObject(ByteUtils.InstanceObjectMapper().writeValueAsBytes(user));
            TcpProtocol tcpProtocol = new TcpProtocol();
            byte [] objectBytes= ByteUtils.InstanceObjectMapper().writeValueAsBytes(dtObject);
            tcpProtocol.setLen(objectBytes.length);
            tcpProtocol.setData(objectBytes);

            ctx.writeAndFlush(tcpProtocol);
        }catch (Exception e){
            log.info("生成对象出现问题："+e);
        }

    }


    /**
     * 读取数据
     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info("InBusinessHandler# # 给客户端 发送数据 ... :" + ctx.channel() + "==>数据：" + msg.toString());
//        ctx.channel().writeAndFlush(msg.toString());
//
////        JSONObject param = null;
////        try{
////            param = JSONObject.parseObject((String) msg);
////        }catch (Exception e){
////            ctx.channel().close();
////            return;
////        }
////        if (param == null) {
////            ctx.channel().close();
////            return;
////        }
////        Integer type = JSONObject.parseObject(param.get("type").toString(), Integer.class);
////        Object data = param.get("data");
////        switch (type) {
////            // 心跳
////            case 0:
////                receiptMsg(ctx, 0);
////                break;
////            // 登陆
////            case 1:
////                nettyLogin(ctx, data);
////                break;
////            default:
////                ctx.channel().close();
////                break;
////        }
//
////        DeviceSession session = ctx.channel().attr(KEY).get();     // 检测是否 自己注册的 客户端
////
////        ByteBuf buffer = (ByteBuf) msg;
////
////        if (buffer == null || session == null) {
////            closeConnection(ctx); // 关闭连接
////        }
////
////        MsgEntity msgEntity = new MsgEntity(buffer); // 解码  buffer 封装  msgEntity
////        log.info("# Accept Client data :" + msgEntity.toString());
////
////        if (MsgType.UNKNOW == msgEntity.getMsgType()) {
////            log.info("# 客户端 发送数据 类型未定义... :" + msgEntity.toString());
////            return;
////        }
////
////        if (!session.isActivity()) {
////            session.setActivity(true);
////            session.setImei(msgEntity.getImei());
////            SessionManager.getSingleton().addClient(session);
////        }
////        producer.putData(msgEntity);
//
//    }

    private void nettyLogin(ChannelHandlerContext ctx, Object data) {
        if(ObjectUtils.isEmpty(data)){
            ctx.channel().close();
            return;
        }
//        JSONObject jsonObject = JSONObject.parseObject(data.toString());
//        Object userId1 = jsonObject.get("userId");
//        Long userId = JSON.parseObject(userId1.toString(), Long.class);
//        UserInfoVo userInfoVo = appUserService.getUserInfoById(userId);
        Device device = new Device(ctx.channel().toString()+System.currentTimeMillis(),System.currentTimeMillis()+"");
        if (ChannelManager.deviceChannels.containsKey(device.getUniqueNo())){
            Channel channel = ChannelManager.deviceChannels.get(device.getUniqueNo());
            // TODO 需要定义返回的JSON格式，通知用户被挤下去了
            channel.close();
        }
        ctx.channel().attr(ChannelManager.deviceInfoVoAttr).set(device);
        ChannelManager.deviceChannels.put(device.getUniqueNo(), ctx.channel());
        receiptMsg(ctx, 1);
    }

    private void receiptMsg(ChannelHandlerContext ctx, int type) {
        JSONObject resultJson = new JSONObject();
        JSONObject resutlObj = new JSONObject();
        resutlObj.put("code", 0);
        resutlObj.put("msg", "success");

        resultJson.put("type", type);
        resultJson.put("data", resutlObj);
        TextWebSocketFrame homeownerResp = new TextWebSocketFrame(resultJson.toJSONString());
        ctx.channel().writeAndFlush(homeownerResp);
    }







}
