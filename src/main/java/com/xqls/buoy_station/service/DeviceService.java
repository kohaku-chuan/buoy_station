package com.xqls.buoy_station.service;

import com.xqls.buoy_station.entity.ModBusObject;

public interface DeviceService {
    /**
     * 设备离线
     */
    boolean makeDeviceOffline(Integer address);

    /**
     * 设备读取更新
     */
    boolean updateModBusRead(ModBusObject modBusObject);

    /**
     * 通过地址号获取modbus设备信息
     */
    ModBusObject getDeviceByAddress(Integer address);
}
