package com.xqls.buoy_station.netty.tcp;

import com.xqls.buoy_station.dto.CommonDto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Kohaku_川
 * @description
 * @date 2022/3/23 9:35
 */
public class CommonEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object dto, ByteBuf byteBuf) {
        CommonDto dta = (CommonDto) dto;
        dta.getEncode(byteBuf);
    }
}
