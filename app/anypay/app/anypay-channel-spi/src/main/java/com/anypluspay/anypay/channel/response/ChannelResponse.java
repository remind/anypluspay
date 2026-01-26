package com.anypluspay.anypay.channel.response;

import lombok.Data;

/**
 * 网关专用结果
 *
 * @author wxj
 * 2024/9/15
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
     * 渠道返回错误代码
     **/
    private String errCode;

    /**
     * 渠道返回错误信息
     **/
    private String errMsg;

}
