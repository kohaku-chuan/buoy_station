package com.xqls.buoy_station.entity;

import lombok.Data;

/**
 * @author Kohaku_川
 * @description TODO 异常记录实体类
 * @date 2022/3/23 10:24
 */
@Data
public class ExceptionRecord {
    /**
     * 地址
     */
    Integer address;

    /**
     * 类型
     */
    String type;

    /**
     * 内容
     */
    String content;

    /**
     * 时间
     */
    String time;

    public ExceptionRecord(Integer address, String type, String content) {
        this.address = address;
        this.type = type;
        this.content = content;
    }
}
