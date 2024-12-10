package com.anypluspay.channelgateway.test;

import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.GatewayOrder;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.commons.lang.types.Money;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/7/11
 */
@Service
public class LocalBankQueryGateway extends AbstractLocalBankGateway implements QueryGateway {
    @Override
    public void query(GatewayRequest<GatewayOrder> gatewayRequest, GatewayOrder gatewayOrder, GatewayResult result) {
        result.setSuccess(true);
        if (isTest(gatewayOrder)) {
            testProcess(gatewayOrder, result);
        }
    }

    private void testProcess(GatewayOrder gatewayOrder, GatewayResult result) {
        TestFlag testFlag = getTestFlag(gatewayOrder);
        if (TestConstants.S.equals(testFlag.getQ())) {
            result.setApiCode("SUCCESS");
        } else if (TestConstants.F.equals(testFlag.getQ())) {
            result.setApiCode("ORDER_CLOSED");
            result.setApiMessage("订单已关闭");
        } else if (TestConstants.QUERY_MONEY_NOT_EQUAL.equals(testFlag.getQ())) {
            result.setApiCode("SUCCESS");
            result.setRealAmount(gatewayOrder.getAmount().add(new Money(1)));
        } else {
            result.setApiCode("unkown");
            result.setApiMessage("未知");
        }
    }
}
