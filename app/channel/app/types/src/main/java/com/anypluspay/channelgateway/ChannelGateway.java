package com.anypluspay.channelgateway;

import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.result.GatewayResult;

/**
 * 渠道网关
 *
 * @author wxj
 * 2024/9/13
 */
public interface ChannelGateway<T extends RequestContent> {

    /**
     * 调用网关，异常可向外抛，在外统一捕获处理
     *
     * @param gatewayRequest 网关参数
     * @return 网关结果
     */
    GatewayResult call(GatewayRequest<T> gatewayRequest) throws Exception;

}
