package com.anypluspay.channel.application.institution.gateway;

import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.result.CsResultCode;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 网关分发器
 *
 * @author wxj
 * 2024/7/9
 */
@Service
@Slf4j
public class GatewayRequestDispatcher {

    @Autowired
    private GatewayProxy gatewayProxy;

    /**
     * 执行分发
     *
     * @param channelApiContext
     * @param requestContent
     * @return
     */
    public GatewayResult doDispatch(ChannelApiContext channelApiContext, RequestContent requestContent) {
        GatewayResult gatewayResult;
        int retryCount = 0;
        do {
            try {
                gatewayResult = gatewayProxy.invoke(channelApiContext.getChannelApi(), requestContent);
                gatewayResult.setChannelCode(channelApiContext.getChannelCode());
                gatewayResult.setApiType(channelApiContext.getChannelApiType());
            } catch (Exception e) {
                log.error("网关分发异常", e);
                gatewayResult = buildInvokeFailResult(channelApiContext);
            }
            retryCount++;
        } while (canRetry(channelApiContext.getChannelApi(), gatewayResult, retryCount));
        return gatewayResult;
    }

    /**
     * 是否可重试
     *
     * @param channelApi
     * @param gatewayResult
     * @param retryCount
     * @return
     */
    private boolean canRetry(ChannelApi channelApi, GatewayResult gatewayResult, int retryCount) {
        return !gatewayResult.isSuccess() && retryCount < 1 && channelApi.getType() == ChannelApiType.SIGN;
    }

    /**
     * 构造失败的执行器调用结果
     *
     * @return
     */
    private GatewayResult buildInvokeFailResult(ChannelApiContext channelApiContext) {
        GatewayResult gatewayResult = new GatewayResult();
        gatewayResult.setSuccess(false);
        gatewayResult.setChannelCode(channelApiContext.getChannelCode());
        gatewayResult.setApiType(channelApiContext.getChannelApiType());
        gatewayResult.setApiCode(CsResultCode.INVOKE_FAIL.getCode());
        gatewayResult.setApiMessage(CsResultCode.INVOKE_FAIL.getMessage());
        gatewayResult.setReceiveTime(LocalDateTime.now());
        return gatewayResult;
    }
}
