package com.anypluspay.channel.application.institution.gateway;

import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.result.ProcessResult;
import com.anypluspay.channelgateway.request.RequestContent;

import java.util.List;

/**
 * 渠道网关调用增强
 *
 * @author wxj
 * 2024/7/9
 */
public interface GatewayRequestAdvice<T extends RequestContent, R extends ProcessResult> {

    /**
     * 调用前处理
     * @param channelApiContext
     * @param orderContext
     * @param requestContent
     */
    void preHandle(ChannelApiContext channelApiContext, OrderContext orderContext, T requestContent);

    /**
     * 调用后处理
     * @param channelApiContext
     * @param orderContext
     * @param result
     */
    void afterCompletion(ChannelApiContext channelApiContext, OrderContext orderContext, R result);

    /**
     * 支持的api类型
     * @return
     */
    List<ChannelApiType> supportApiType();
}
