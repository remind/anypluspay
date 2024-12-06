package com.anypluspay.channelgateway.request;

import com.anypluspay.channel.types.channel.ChannelApiType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wxj
 * 2024/9/15
 */
@Data
public class GatewayRequest<T> implements Serializable {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 接口类型
     */
    private ChannelApiType channelApiType;

    /**
     * 请求内容
     */
    private T content;
}
