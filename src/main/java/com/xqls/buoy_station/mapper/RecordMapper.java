package com.xqls.buoy_station.mapper;

import com.xqls.buoy_station.entity.ExceptionRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordMapper {
    int insertExceptionRecord(ExceptionRecord exceptionRecord);

    int savePacketRecord(@Param("address") Integer address, @Param("type") String type, @Param("content") String receiveStr);
}
