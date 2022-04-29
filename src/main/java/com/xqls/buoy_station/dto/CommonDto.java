package com.xqls.buoy_station.dto;

import com.xqls.buoy_station.util.DecoderUtil;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kohaku_川
 * @description
 * @date 2022/3/23 9:29
 */
@Slf4j
public class CommonDto {

    public void getEncode(ByteBuf byteBuf) {
        this.encode(byteBuf, new StringBuilder());
    }

    public void encode(ByteBuf out, StringBuilder outStr) {
        //计算校验
        String check = DecoderUtil.getModbusCRC(outStr.toString());
        //检验和
        outStr.append(check);
        log.info("发送指令：" + outStr.toString().toUpperCase());
        out.writeBytes(DecoderUtil.hexStr2bytes(outStr.toString().toUpperCase()));
    }
}
