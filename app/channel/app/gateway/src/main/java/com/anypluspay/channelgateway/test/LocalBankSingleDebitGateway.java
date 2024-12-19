package com.anypluspay.channelgateway.test;

import cn.hutool.core.lang.UUID;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.NormalContent;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/12/17
 */
@Service
public class LocalBankSingleDebitGateway extends AbstractLocalBankGateway implements ChannelGateway<NormalContent> {
    @Override
    public GatewayResult call(GatewayRequest<NormalContent> gatewayRequest) {
        GatewayResult result = new GatewayResult();
        result.setSuccess(true);
        if (isTest(gatewayRequest.getContent())) {
            testProcess(gatewayRequest.getContent(), result);
        }
        return result;
    }

    private void testProcess(NormalContent normalContent, GatewayResult result) {
        TestFlag testFlag = getTestFlag(normalContent);
        if (TestConstants.S.equals(testFlag.getD())) {
            result.setInstResponseNo(UUID.fastUUID().toString(true));
            result.setApiCode("SUCCESS");
        } else if (TestConstants.F.equals(testFlag.getD())) {
            result.setApiCode("PARAM_ERROR");
            result.setApiMessage("参数错误");
        } else {
            result.setApiCode("unkown");
            result.setApiMessage("未知");
        }
    }
}
