package com.wujie.netty.client;

import com.wujie.netty.client.business.EchoHandler;
import com.wujie.netty.client.business.InBusinessHandler;
import com.wujie.netty.client.business.WjEchoHandler;
import com.wujie.netty.client.decoder.DecoderHandler;
import com.wujie.netty.client.encoder.EncoderHandler;
import com.wujie.netty.client.encoder.WjEncoderHandler;
import com.wujie.netty.utils.FileUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Properties;

public class TcpClient {

    private String ip;
    private int port;
    public  void init() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.handler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
//                ch.pipeline().addLast("logging",new LoggingHandler("DEBUG"));
                ch.pipeline().addLast(new WjEncoderHandler());
                ch.pipeline().addLast(new WjEchoHandler());
                ch.pipeline().addLast(new DecoderHandler());//解码器，接收消息时候用
                ch.pipeline().addLast(new InBusinessHandler());//业务处理类，最终的消息会在这个handler中进行业务处理
            }
        });
        bootstrap.remoteAddress(ip,port);
        ChannelFuture future = bootstrap.connect().sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public TcpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        Properties properties = FileUtils.readFile("E:\\config.properties");
        if(properties != null)
            new TcpClient(properties.getProperty("ip"),Integer.valueOf(properties.getProperty("port"))).init();
        else
            new TcpClient("127.0.0.1",8777).init();
    }
}
