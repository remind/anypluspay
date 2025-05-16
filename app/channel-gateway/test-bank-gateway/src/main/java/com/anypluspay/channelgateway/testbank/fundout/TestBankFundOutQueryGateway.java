package com.anypluspay.channelgateway.testbank.fundout;

import cn.hutool.core.lang.UUID;
import com.anypluspay.channelgateway.AbstractTestBank;
import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.FundOutContent;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2025/5/16
 */
@Service
public class TestBankFundOutQueryGateway extends AbstractTestBank implements ChannelGateway<FundOutContent> {
    @Override
    public GatewayResult call(GatewayRequest<FundOutContent> gatewayRequest) {
        GatewayResult result = new GatewayResult();
        result.setSuccess(true);
        result.setInstResponseNo(UUID.fastUUID().toString(true));
        result.setApiCode("SUCCESS");
        return result;
    }

}
