package com.anypluspay.channelgateway.api.verify;

import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.StringInfo;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channel.types.channel.ChannelApiType;

/**
 * @author wxj
 * 2024/9/16
 */
public abstract class VerifySignGateway implements ChannelGateway<StringInfo> {
    @Override
    public GatewayResult call(GatewayRequest<StringInfo> gatewayRequest) {
        VerifySignResult result = new VerifySignResult();
        notify(gatewayRequest, result);
        return result;
    }

    @Override
    public boolean support(String channelCode, ChannelApiType channelApiType) {
        return channelApiType == ChannelApiType.VERIFY_SIGN;
    }

    public abstract void notify(GatewayRequest<StringInfo> request, VerifySignResult result);

}
