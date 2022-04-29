package com.xqls.buoy_station.mapper;

import com.xqls.buoy_station.entity.ModBusObject;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceMapper {
    int makeDeviceOffline(Integer address);

    int updateModBusRead(ModBusObject modBusObject);

    ModBusObject getDeviceByAddress(Integer address);
}
