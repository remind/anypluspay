package com.anypluspay.anypay.domain.payment.instruct;

import lombok.Data;

/**
 * @author wxj
 * 2025/12/11
 */
@Data
public class ChannelInstruct extends PayInstruct {

    /**
     * 支付方式ID
     */
    private String payMethodId;

    /**
     * 渠道ID
     */
    private String channelId;
}
