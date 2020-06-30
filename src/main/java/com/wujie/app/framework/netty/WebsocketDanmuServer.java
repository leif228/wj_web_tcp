package com.wujie.app.framework.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Websocket 聊天服务器-服务端
 */
@Component
public class WebsocketDanmuServer {

    private WebsocketDanmuServerInitializer websocketDanmuServerInitializer;

    @Autowired
    public WebsocketDanmuServer(WebsocketDanmuServerInitializer websocketDanmuServerInitializer) {
        this.websocketDanmuServerInitializer = websocketDanmuServerInitializer;
    }

    public void run(){
        
        EventLoopGroup bossGroup = new NioEventLoopGroup(2); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup(3);
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(websocketDanmuServerInitializer)  //(4)
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = null; // (7)
            f = b.bind(8880).sync();
            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}