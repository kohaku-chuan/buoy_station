package com.xqls.buoy_station.dto.send;

import com.xqls.buoy_station.dto.CommonDto;
import com.xqls.buoy_station.util.DecoderUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kohaku_川
 * @description TODO 水质值写入ModBus设备
 * @date 2022/3/23 10:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WriteWaterValueDto extends CommonDto {
    String cod;
    String ph;
    String nh3;
    String ss;

    public WriteWaterValueDto(String cod, String ph, String nh3, String ss) {
        this.cod = cod;
        this.ph = ph;
        this.ss = ss;
        this.nh3 = nh3;
    }

    @Override
    public void encode(ByteBuf out, StringBuilder outStr) {
        outStr.append("01100002000408")
                .append(DecoderUtil.doubleTo4321Hex(Double.valueOf(this.getCod())))
                .append(DecoderUtil.doubleTo4321Hex(Double.valueOf(this.getSs())))
                .append(DecoderUtil.doubleTo4321Hex(Double.valueOf(this.getNh3())))
                .append(DecoderUtil.doubleTo4321Hex(Double.valueOf(this.getPh())));
        super.encode(out, outStr);
    }
}
