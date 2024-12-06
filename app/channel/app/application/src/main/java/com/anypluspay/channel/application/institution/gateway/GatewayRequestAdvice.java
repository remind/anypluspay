package com.anypluspay.channel.application.institution.gateway;

import com.anypluspay.channel.types.result.ProcessResult;
import com.anypluspay.channelgateway.request.OrderInfo;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.types.channel.ChannelApiType;

/**
 * 渠道网关调用增强
 *
 * @author wxj
 * 2024/7/9
 */
public interface GatewayRequestAdvice<T extends OrderInfo, R extends ProcessResult> {

    void preHandle(ChannelApiContext channelApiContext, OrderContext orderContext, T OrderInfo);

    void afterCompletion(ChannelApiContext channelApiContext, OrderContext orderContext, R result);

    ChannelApiType supportApiType();
}