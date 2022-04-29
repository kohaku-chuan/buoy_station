package com.xqls.buoy_station.netty;

import com.xqls.buoy_station.netty.tcp.TcpServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kohaku_川
 * @description TODO
 * @date 2022/3/23 18:07
 */
@Slf4j
public class NettyServer {

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            //TCP设置
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childHandler(new TcpServerChannelInitializer());
            Channel channel1 = bootstrap.bind(6025).sync().channel();
            log.info("TCP服务启动");
            channel1.closeFuture().sync();
        } catch (Exception e) {
            log.error("Netty服务异常---------" + e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
