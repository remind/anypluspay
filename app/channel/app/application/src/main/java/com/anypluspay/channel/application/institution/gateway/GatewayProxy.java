package com.anypluspay.channel.application.institution.gateway;

import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.result.GatewayResult;

/**
 *  网关代理
 * @author wxj
 * 2024/7/9
 */
public interface GatewayProxy {

    /**
     * 调用网关
     * @param channelApi
     * @param content
     * @return
     */
    GatewayResult invoke(ChannelApi channelApi, RequestContent content);

}
