package com.xqls.buoy_station.netty;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Kohaku_川
 * @description TODO
 * @date 2022/3/23 18:08
 */
@Component
public class NettyServerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            NettyServer nettyServer = new NettyServer();
            nettyServer.start();
        }
    }
}
