package com.wujie.netty.client.business;

import com.alibaba.fastjson.JSONObject;
import com.wujie.netty.client.TcpClient;
import com.wujie.netty.dataTransefer.DTObject;
import com.wujie.netty.pojo.LoginTask;
import com.wujie.netty.pojo.User;
import com.wujie.netty.protocol.TcpProtocol;
import com.wujie.netty.protocol.WjProtocol;
import com.wujie.netty.utils.ByteUtils;
import com.wujie.netty.utils.FileUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;
@Slf4j
public class WjEchoHandler extends ChannelInboundHandlerAdapter {

    //连接成功后发送消息测试
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        WjProtocol wjProtocol = new WjProtocol();
        wjProtocol.setPlat(Short.parseShort("20"));
        wjProtocol.setMaincmd(Short.parseShort("0"));
        wjProtocol.setSubcmd(Short.parseShort("1"));
        wjProtocol.setFormat("JS");
        wjProtocol.setBack(Short.parseShort("0"));

        LoginTask loginTask = new LoginTask();
        Properties properties=FileUtils.readFile("E:\\config.properties");
        if(properties != null)
            loginTask.setOid(properties.getProperty("fzwno"));
        else
            loginTask.setOid("88888888");

//        byte [] objectBytes= ByteUtils.InstanceObjectMapper().writeValueAsBytes(loginTask);

        String jsonStr = JSONObject.toJSONString(loginTask);
        log.debug(jsonStr);
        byte [] objectBytes= jsonStr.getBytes();

        int len = 21+objectBytes.length;
        wjProtocol.setLen((short) len);
        wjProtocol.setUserdata(objectBytes);

        ctx.write(wjProtocol);

        ctx.flush();


    }
}
