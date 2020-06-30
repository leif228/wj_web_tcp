package com.wujie.app.framework.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerIdleStateTrigger extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    // eventType = "读空闲";
                    ctx.channel().close();
                    break;
                case WRITER_IDLE:
                    // eventType = "写空闲";
                    // 不处理
                    break;
                case ALL_IDLE:
                    // eventType = "读写空闲";
                    // 不处理
                    break;
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
