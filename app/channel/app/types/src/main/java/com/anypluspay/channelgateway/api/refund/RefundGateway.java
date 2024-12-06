package com.anypluspay.channelgateway.api.refund;

import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.types.channel.ChannelApiType;

/**
 * @author wxj
 * 2024/9/16
 */
public interface RefundGateway extends ChannelGateway<RefundOrder> {
    @Override
    default GatewayResult call(GatewayRequest<RefundOrder> gatewayRequest) {
        GatewayResult result = new GatewayResult();
        result.setInstRequestNo(gatewayRequest.getContent().getInstRequestNo());
        refund(gatewayRequest, gatewayRequest.getContent(), result);
        return result;
    }

    @Override
    default boolean support(String channelCode, ChannelApiType channelApiType) {
        return channelApiType == ChannelApiType.SINGLE_REFUND;
    }

    void refund(GatewayRequest<RefundOrder> gatewayRequest, RefundOrder refundOrder, GatewayResult result);
}
