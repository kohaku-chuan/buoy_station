package com.xqls.buoy_station.netty.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Kohaku_川
 * @description TODO TCP服务初始化
 * @date 2022/3/23 9:40
 */
public class TcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline().addLast(new TcpServerHandler());
        socketChannel.pipeline().addLast(new CommonEncoder());
    }
}
