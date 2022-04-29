package com.xqls.buoy_station.util;

import com.xqls.buoy_station.dto.CommonDto;
import io.netty.channel.Channel;

/**
 * @author Kohaku_川
 * @description TODO
 * @date 2022/3/25 17:02
 */
public class SendMsgUtil {

    private static RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);

    public static Boolean sendControl(String address, CommonDto commonDto) {
        Channel channel = ChannelCache.getChannel(address);
        if (channel == null) {
            return false;
        }
        try {
            int wait = 10;
            do {
                if (redisUtil.hget(CustomString.BUSY, channel.id().asLongText()) != null) {
                    wait--;
                } else {
                    wait = 0;
                }
            } while (wait > 0);
            channel.writeAndFlush(commonDto);
            redisUtil.hset(CustomString.BUSY, channel.id().asLongText(), "1");
            boolean response = false;
            int times = 10;
            do {
                if (redisUtil.hasKey(channel.id().toString() + CustomString.FEEDBACK)) {
                    response = true;
                } else {
                    Thread.sleep(1000);
                    times--;
                }
            } while (!response && times > 0);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisUtil.hdel(CustomString.BUSY, channel.id().asLongText());
        }
        return false;
    }
}
