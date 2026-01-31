package com.anypluspay.anypay.domain.channel.spi.response;

import lombok.Data;

/**
 * 渠道响应结果
 *
 * @author wxj
 * 2026/1/30
 */
@Data
public class ChannelResponse {

    /**
     * 订单状态
     */
    private ChannelOrderStatus status;

    /**
     * 提交渠道订单号
     */
    private String channelRequestNo;

    /**
     * 渠道返回流水号
     */
    private String channelResponseNo;

    /**
     * 渠道返回结果代码
     **/
    private String resultCode;

    /**
     * 渠道返回结果信息
     **/
    private String resultMsg;
}
