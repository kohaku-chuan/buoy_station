package com.xqls.buoy_station.entity;

import com.xqls.buoy_station.util.DecoderUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kohaku_川
 * @description TODO modbus设备类
 * @date 2022/3/23 9:58
 */
@Data
@Slf4j
public class ModBusObject {

    /**
     * 通讯IP
     */
    String ip;

    /**
     * 通讯端口
     */
    Integer port;

    /**
     * 设备地址
     */
    Integer address;

    /**
     * 设备通讯状态
     */
    String network;

    /**
     * 下写时间
     */
    String writeTime;

    /**
     * 上次断线时间
     */
    String offlineTime;

    public static ModBusObject decoder(ByteBuf in) {
        try {
            ModBusObject modBusObject = new ModBusObject();
            modBusObject.setNetwork("在线");
            modBusObject.setAddress(DecoderUtil.byte2ToInt(in));
            return modBusObject;
        } catch (Exception e) {
            log.error("ModBus设备读取解析异常！");
            return null;
        }
    }
}
