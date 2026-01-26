package com.anypluspay.anypay.channel.request;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

@Data
public class ChannelUnifiedOrderRequest {

    /**
     * 渠道请求订单号
     */
    private String channelRequestNo;

    /**
     * 金额
     */
    private Money amount;
}
