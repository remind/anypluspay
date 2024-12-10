package com.anypluspay.channelgateway.api.query;

import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayOrder;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;

/**
 * 查询网关
 *
 * @author wxj
 * 2024/9/15
 */
public interface QueryGateway extends ChannelGateway<GatewayOrder> {

    @Override
    default GatewayResult call(GatewayRequest<GatewayOrder> gatewayRequest) {
        GatewayResult result = new GatewayResult();
        result.setInstRequestNo(gatewayRequest.getContent().getInstRequestNo());
        query(gatewayRequest, gatewayRequest.getContent(), result);
        return result;
    }

    void query(GatewayRequest<GatewayOrder> gatewayRequest, GatewayOrder gatewayOrder, GatewayResult result);
}
