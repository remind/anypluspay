package com.anypluspay.channelgateway.test;

import com.anypluspay.channelgateway.api.sign.SignGateway;
import com.anypluspay.channelgateway.api.sign.SignNormalContent;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/12/9
 */
@Service
public class LocalBankRefundVerifySignGateway extends AbstractLocalBankGateway implements SignGateway {
    @Override
    public void sign(GatewayRequest<SignNormalContent> gatewayRequest, SignNormalContent signOrderInfo, SignResult result) {

    }
}
