package com.anypluspay.channel.facade.request;

import lombok.Data;

/**
 * 通知请求
 *
 * @author wxj
 * 2025/6/4
 */
@Data
public class NotifyRequest {

    /**
     * 机构请求号
     */
    private String instRequestNo;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 渠道接口类型
     */
    private String channelApiType;

    /**
     * 回调类型
     */
    private String callbackType;

    /**
     * 请求报文
     */
    private String requestBody;
}
