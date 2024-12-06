package com.anypluspay.channelgateway.test;

import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channelgateway.api.refund.RefundGateway;
import com.anypluspay.channelgateway.api.refund.RefundOrder;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/7/14
 */
@Service
public class TestOnlineBankRefundGateway implements RefundGateway {
    @Override
    public void refund(GatewayRequest<RefundOrder> gatewayRequest, RefundOrder refundOrder, GatewayResult result) {
        result.setSuccess(true);
        if (isTest(refundOrder)) {
            testProcess(refundOrder, result);
        }
    }

    private void testProcess(RefundOrder refundOrder, GatewayResult result) {
        TestFlag testFlag = getTestFlag(refundOrder);
        if (TestConstants.S.equals(testFlag.getD())) {
            result.setApiCode("SUCCESS");
        } else if (TestConstants.P.equals(testFlag.getD())) {
            result.setApiCode("PROCESS");
        } else if (TestConstants.F.equals(testFlag.getD())) {
            result.setApiCode("PARAM_ERROR");
            result.setApiMessage("参数错误");
        } else {
            result.setApiCode("unkown");
            result.setApiMessage("未知");
        }
    }
}
