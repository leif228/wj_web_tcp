package com.wujie.app.framework.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 服务端 ChannelInitializer
 */
@Component
public class WebsocketDanmuServerInitializer extends ChannelInitializer<SocketChannel> {


    private TextWebSocketFrameHandler textWebSocketFrameHandler;

    @Autowired
    public WebsocketDanmuServerInitializer(TextWebSocketFrameHandler textWebSocketFrameHandler) {
        this.textWebSocketFrameHandler = textWebSocketFrameHandler;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {//2
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("http-decodec", new HttpRequestDecoder());
        pipeline.addLast("http-aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("http-encodec", new HttpResponseEncoder());

	   /*
	   * pipeline.addLast("http-chunked", new ChunkedWriteHandler());
			pipeline.addLast(new HttpServerCodec());
			pipeline.addLast(new HttpObjectAggregator(64*1024));
			pipeline.addLast(new ChunkedWriteHandler());
	   * */
        pipeline.addLast("http-request", new HttpRequestHandler("/ws"));
        pipeline.addLast("WebSocket-protocol", new WebSocketServerProtocolHandler("/ws"));
        // 每60秒检测一下这个通道是否活跃
        pipeline.addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast("idleStateTrigger", new ServerIdleStateTrigger());
        pipeline.addLast("WebSocket-request", textWebSocketFrameHandler);
    }
}
