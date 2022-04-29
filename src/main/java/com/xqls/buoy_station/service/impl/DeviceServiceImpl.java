package com.xqls.buoy_station.service.impl;

import com.xqls.buoy_station.entity.ModBusObject;
import com.xqls.buoy_station.mapper.DeviceMapper;
import com.xqls.buoy_station.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kohaku_川
 * @description
 * @date 2022/3/23 9:28
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    DeviceMapper mapper;

    @Override
    public boolean makeDeviceOffline(Integer address) {
        return mapper.makeDeviceOffline(address) > 0;
    }

    @Override
    public boolean updateModBusRead(ModBusObject modBusObject) {
        return mapper.updateModBusRead(modBusObject) > 0;
    }

    @Override
    public ModBusObject getDeviceByAddress(Integer address) {
        return mapper.getDeviceByAddress(address);
    }
}
