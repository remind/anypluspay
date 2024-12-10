package com.anypluspay.channelgateway.api.refund;

import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;

/**
 * @author wxj
 * 2024/9/16
 */
public interface RefundGateway extends ChannelGateway<RefundGatewayOrder> {
    @Override
    default GatewayResult call(GatewayRequest<RefundGatewayOrder> gatewayRequest) {
        GatewayResult result = new GatewayResult();
        result.setInstRequestNo(gatewayRequest.getContent().getInstRequestNo());
        refund(gatewayRequest, gatewayRequest.getContent(), result);
        return result;
    }

    void refund(GatewayRequest<RefundGatewayOrder> gatewayRequest, RefundGatewayOrder refundOrder, GatewayResult result);
}
