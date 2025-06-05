package com.anypluspay.channelgateway.alipay;

import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.NormalContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2025/6/4
 */
@Service
public class AlipayQueryGateway extends AbstractAlipayGateway implements QueryGateway {
    @Override
    public void query(GatewayRequest<NormalContent> gatewayRequest, NormalContent normalContent, GatewayResult result) {

    }
}
