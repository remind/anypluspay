package com.anypluspay.anypay.domain.channel.spi.response;

import lombok.Data;

/**
 * @author wxj
 * 2026/1/30
 */
@Data
public class ChannelNotifyResponse extends ChannelResponse {

    /**
     * 响应报文
     */
    private String body;
}
