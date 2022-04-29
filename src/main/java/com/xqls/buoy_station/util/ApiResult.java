package com.xqls.buoy_station.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Kohaku_川
 * @description TODO 请求接口返回封装类
 * @date 2022/3/23 17:51
 */
@Data
public class ApiResult implements Serializable {
    /**
     * 请求结果
     */
    boolean success;

    /**
     * 错误信息
     */
    String error;

    /**
     * 返回数据
     */
    Object data;

}
