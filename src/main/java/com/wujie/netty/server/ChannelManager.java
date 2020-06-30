package com.wujie.netty.server;

import com.wujie.netty.pojo.Device;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelManager {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static Map<String, Channel> deviceChannels = new ConcurrentHashMap<>();
    public final static AttributeKey<Device> deviceInfoVoAttr = AttributeKey.valueOf("DEVICE_INFO_KEY");
}
