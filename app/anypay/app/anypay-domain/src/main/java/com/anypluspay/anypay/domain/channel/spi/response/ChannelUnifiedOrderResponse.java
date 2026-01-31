package com.anypluspay.anypay.domain.channel.spi.response;

import lombok.Data;

/**
 * 渠道统一下单、扣款响应结果
 *
 * @author wxj
 * 2024/9/15
 */
@Data
public class ChannelUnifiedOrderResponse extends ChannelResponse {

    /**
     * 渠道返回参数
     **/
    private String channelParam;

}
