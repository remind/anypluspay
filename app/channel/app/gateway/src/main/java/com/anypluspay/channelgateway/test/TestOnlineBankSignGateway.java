package com.anypluspay.channelgateway.test;

import com.anypluspay.channelgateway.api.sign.SignGateway;
import com.anypluspay.channelgateway.api.sign.SignOrderInfo;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.OrderInfo;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/9/15
 */
@Service
public class TestOnlineBankSignGateway implements SignGateway {
    @Override
    public void sign(GatewayRequest<SignOrderInfo> gatewayRequest, SignOrderInfo signOrderInfo, SignResult result) {
        result.setSuccess(true);
        if (isTest(signOrderInfo)) {
            testProcess(signOrderInfo, result);
        }
    }

    private void testProcess(OrderInfo orderInfo, SignResult result) {
        TestFlag testFlag = getTestFlag(orderInfo);
        if (TestConstants.S.equals(testFlag.getD())) {
            String ret = "{\\\"prepay_id\\\":\\\"wx26112221580621e9b071c00d9e093b0000\\\"}";
            result.setInstPageUrl(ret);
            result.setInstResponseNo("wx26112221580621e9b071c00d9e093b0000");
            result.setApiCode("200");
        } else if (TestConstants.F.equals(testFlag.getD())) {
            result.setApiCode("PARAM_ERROR");
            result.setApiMessage("参数错误");
        } else {
            result.setApiCode("unkown");
            result.setApiMessage("未知");
        }
    }
}
