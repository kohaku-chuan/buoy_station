package com.xqls.buoy_station.service;

import com.xqls.buoy_station.entity.ExceptionRecord;

public interface RecordService {
    /**
     * 新增异常记录
     */
    boolean insertExceptionRecord(ExceptionRecord exceptionRecord);

    /**
     * 保存读取到的报文
     */
    boolean savePacketRecord(Integer address, String type, String receiveStr);
}
