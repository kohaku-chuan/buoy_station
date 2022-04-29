package com.xqls.buoy_station.service.impl;

import com.xqls.buoy_station.entity.ExceptionRecord;
import com.xqls.buoy_station.mapper.RecordMapper;
import com.xqls.buoy_station.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kohaku_川
 * @description TODO
 * @date 2022/3/23 13:14
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper mapper;

    @Override
    public boolean insertExceptionRecord(ExceptionRecord exceptionRecord) {
        return mapper.insertExceptionRecord(exceptionRecord) > 0;
    }

    @Override
    public boolean savePacketRecord(Integer address, String type, String receiveStr) {
        return mapper.savePacketRecord(address, type, receiveStr) > 0;
    }
}
