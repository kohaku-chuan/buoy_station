package com.xqls.buoy_station.controller;

import com.alibaba.fastjson.JSONObject;
import com.xqls.buoy_station.dto.CommonDto;
import com.xqls.buoy_station.dto.send.WriteWaterValueDto;
import com.xqls.buoy_station.service.RecordService;
import com.xqls.buoy_station.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Kohaku_川
 * @description TODO
 * @date 2022/3/23 17:05
 */
@RestController
@RequestMapping("buoyStationApi")
public class ApiController {
    @Autowired
    RecordService recordService;
    @Autowired
    RedisUtil redisUtil;

    /**
     * @param
     * @description 获取实时数据列表
     * @Author kohaku_川
     * @date 2021/6/18
     */
    @PostMapping(path = "writeToBuoyStation")
    public ApiResult writeToBuoyStation(HttpServletRequest request, @RequestBody Map<String, String> param) {
        ApiResult apiResult = new ApiResult();
        String ip = DecoderUtil.getIpAddress(request);
        String content = "服务调用正常，写入设备成功";
        String msg = "";
        if (!ip.equals(CustomString.SERVER_IP)) {
            apiResult.setSuccess(false);
            apiResult.setError("非法请求,请求IP:" + ip);
            return apiResult;
        }
        try {
            String address = param.get("address");
            String cod = param.get("cod");
            String ss = param.get("ss");
            String ph = param.get("ph");
            String nh3 = param.get("nh3");
            msg = "【COD：" + cod + "，SS：" + ss + "，pH：" + ph + "，NH3：" + nh3 + "】";
            WriteWaterValueDto writeWaterValueDto = new WriteWaterValueDto(cod, ph, nh3, ss);
            ApiResult sendResult = sendDto(address, writeWaterValueDto);
            if (!sendResult.isSuccess()) {
                content = "服务调用正常，设备离线，等待重连后自动下发写入";
                redisUtil.hset(CustomString.UN_SEND, address, JSONObject.toJSONString(writeWaterValueDto));
            }
            apiResult.setData(sendResult.isSuccess() ? CustomString.SUCCESS : CustomString.FAULT);
            apiResult.setSuccess(true);
        } catch (Exception e) {
            content = "服务调用异常(" + e.getMessage() + ")";
            apiResult.setError(e.getMessage());
            apiResult.setSuccess(false);
        }
        recordService.savePacketRecord(Integer.valueOf(param.get("address")), CustomString.WRITE, content + msg);
        return apiResult;
    }

    private ApiResult sendDto(String pumpAddress, CommonDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            boolean send = SendMsgUtil.sendControl(pumpAddress, dto);
            apiResult.setSuccess(send);
        } catch (Exception e) {
            apiResult.setSuccess(false);
            apiResult.setError(e.getMessage());
        }
        return apiResult;
    }

}
