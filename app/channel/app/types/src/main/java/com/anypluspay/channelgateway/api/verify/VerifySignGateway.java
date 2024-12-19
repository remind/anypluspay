package com.anypluspay.channelgateway.api.verify;

import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.StringContent;
import com.anypluspay.channelgateway.result.GatewayResult;

/**
 * @author wxj
 * 2024/9/16
 */
public interface VerifySignGateway extends ChannelGateway<StringContent> {
    @Override
    default GatewayResult call(GatewayRequest<StringContent> gatewayRequest) {
        VerifySignResult result = new VerifySignResult();
        notify(gatewayRequest, result);
        return result;
    }

    void notify(GatewayRequest<StringContent> request, VerifySignResult result);

}
