package com.xqls.buoy_station.netty.tcp;

import com.alibaba.fastjson.JSONObject;
import com.xqls.buoy_station.dto.send.ReadAddressDto;
import com.xqls.buoy_station.dto.send.WriteWaterValueDto;
import com.xqls.buoy_station.entity.ExceptionRecord;
import com.xqls.buoy_station.entity.ModBusObject;
import com.xqls.buoy_station.service.DeviceService;
import com.xqls.buoy_station.service.RecordService;
import com.xqls.buoy_station.util.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author Kohaku_川
 * @description
 * @date 2022/3/23 9:40
 */
@Slf4j
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    private RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
    private DeviceService deviceService = SpringUtil.getBean(DeviceService.class);
    private RecordService recordService = SpringUtil.getBean(RecordService.class);

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.debug("连接+1，主动读取数据。。。");
        Channel channel = ctx.channel();
        channel.writeAndFlush(new ReadAddressDto());
        redisUtil.hset(CustomString.BUSY, channel.id().asLongText(), "1", 5);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Object obj = redisUtil.get(ctx.channel().id().asLongText());
        if (obj != null) {
            ChannelCache.deleteChannel(obj.toString());
            Integer address = Integer.valueOf(obj.toString());
            deviceService.makeDeviceOffline(address);
            recordService.insertExceptionRecord(new ExceptionRecord(address, CustomString.NETWORK, CustomString.OFFLINE_CONTENT));
        }
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        ByteBuf in = (ByteBuf) msg;
        ByteBuf copy = in.copy();
        try {
            byte[] bytes = new byte[copy.readableBytes()];
            copy.readBytes(bytes);
            //转16进制字符串
            String receiveStr = DecoderUtil.receiveHexToString(bytes);
            //16进制转ASCII
            assert receiveStr != null;
            //modbus通讯
            InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.remoteAddress();
            String ip = inetSocketAddress.getAddress().getHostAddress();
            int port = inetSocketAddress.getPort();
            if (receiveStr.length() > 10) {
                redisUtil.set(channel.id().toString()+CustomString.FEEDBACK, "1", 10);
            } else {
                ModBusObject modBusObject = ModBusObject.decoder(in);
                if (modBusObject != null) {
                    recordService.savePacketRecord(modBusObject.getAddress(), CustomString.READ, receiveStr);
                    ModBusObject device = deviceService.getDeviceByAddress(modBusObject.getAddress());
                    if (device != null) {
                        modBusObject.setIp(ip);
                        modBusObject.setPort(port);
                        //离线后上线或第一次上线判断
                        if (CustomString.OFFLINE.equals(device.getNetwork())) {
                            recordService.insertExceptionRecord(new ExceptionRecord(modBusObject.getAddress(), CustomString.NETWORK, CustomString.ONLINE_CONTENT));
                        }
                        deviceService.updateModBusRead(modBusObject);
                    }
                    redisUtil.set(channel.id().asLongText(), modBusObject.getAddress().toString());
                    ChannelCache.checkChannel(modBusObject.getAddress().toString(), channel);
                    Object object = redisUtil.hget(CustomString.UN_SEND, modBusObject.getAddress().toString());
                    if (object != null) {
                        channel.writeAndFlush(JSONObject.parseObject(object.toString(), WriteWaterValueDto.class));
                        redisUtil.hdel(CustomString.UN_SEND, modBusObject.getAddress().toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(in);
            ReferenceCountUtil.release(copy);
        }
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("netty连接异常");
    }

}
