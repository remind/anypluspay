package com.anypluspay.channelgateway.test;

import cn.hutool.core.lang.UUID;
import com.anypluspay.channelgateway.api.sign.SignGateway;
import com.anypluspay.channelgateway.api.sign.SignGatewayOrder;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.GatewayOrder;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/9/15
 */
@Service
public class LocalBankSignGateway implements SignGateway {
    @Override
    public void sign(GatewayRequest<SignGatewayOrder> gatewayRequest, SignGatewayOrder signOrderInfo, SignResult result) {
        result.setSuccess(true);
        if (isTest(signOrderInfo)) {
            testProcess(signOrderInfo, result);
        }
    }

    private void testProcess(GatewayOrder gatewayOrder, SignResult result) {
        TestFlag testFlag = getTestFlag(gatewayOrder);
        if (TestConstants.S.equals(testFlag.getD())) {
            result.setInstPageUrl("pay page url");
            result.setInstResponseNo(UUID.fastUUID().toString(true));
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
