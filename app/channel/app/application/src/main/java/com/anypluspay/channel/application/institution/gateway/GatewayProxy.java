package com.anypluspay.channel.application.institution.gateway;

import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.domain.channel.api.ChannelApi;

/**
 * @author wxj
 * 2024/7/9
 */
public interface GatewayProxy {

    GatewayResult invoke(ChannelApi channelApi, Object content);

}
