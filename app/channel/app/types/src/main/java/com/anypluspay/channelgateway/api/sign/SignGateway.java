package com.anypluspay.channelgateway.api.sign;

import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.types.channel.ChannelApiType;

/**
 * @author wxj
 * 2024/9/15
 */
public interface SignGateway extends ChannelGateway<SignGatewayOrder> {
    @Override
    default GatewayResult call(GatewayRequest<SignGatewayOrder> gatewayRequest) {
        SignResult result = new SignResult();
        result.setInstRequestNo(gatewayRequest.getContent().getInstRequestNo());
        sign(gatewayRequest, gatewayRequest.getContent(), result);
        return result;
    }

    @Override
    default boolean support(String channelCode, ChannelApiType channelApiType) {
        return channelApiType == ChannelApiType.SIGN;
    }

    void sign(GatewayRequest<SignGatewayOrder> gatewayRequest, SignGatewayOrder signOrderInfo, SignResult result);
}
