package com.anypluspay.channelgateway.api.verify;

import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.StringInfo;
import com.anypluspay.channelgateway.result.GatewayResult;

/**
 * @author wxj
 * 2024/9/16
 */
public interface VerifySignGateway extends ChannelGateway<StringInfo> {
    @Override
    default GatewayResult call(GatewayRequest<StringInfo> gatewayRequest) {
        VerifySignResult result = new VerifySignResult();
        notify(gatewayRequest, result);
        return result;
    }

    void notify(GatewayRequest<StringInfo> request, VerifySignResult result);

}
