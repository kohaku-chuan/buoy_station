package com.xqls.buoy_station.dto.send;

import com.xqls.buoy_station.dto.CommonDto;
import io.netty.buffer.ByteBuf;

/**
 * @author Kohaku_川
 * @description TODO 读取设备地址
 * @date 2022/3/23 9:51
 */
public class ReadAddressDto extends CommonDto {
    @Override
    public void encode(ByteBuf out, StringBuilder outStr) {
        outStr.append("010300000001");
        super.encode(out, outStr);
    }
}
