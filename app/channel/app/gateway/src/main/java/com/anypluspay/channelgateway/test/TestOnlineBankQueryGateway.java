package com.anypluspay.channelgateway.test;

import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.OrderInfo;
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
public class TestOnlineBankQueryGateway implements QueryGateway {
    @Override
    public void query(GatewayRequest<OrderInfo> gatewayRequest, OrderInfo orderInfo, GatewayResult result) {
        result.setSuccess(true);
        if (isTest(orderInfo)) {
            testProcess(orderInfo, result);
        }
    }

    private void testProcess(OrderInfo orderInfo, GatewayResult result) {
        TestFlag testFlag = getTestFlag(orderInfo);
        if (TestConstants.S.equals(testFlag.getQ())) {
            result.setApiCode("SUCCESS");
        } else if (TestConstants.F.equals(testFlag.getQ())) {
            result.setApiCode("ORDER_CLOSED");
            result.setApiMessage("订单已关闭");
        } else if (TestConstants.QUERY_MONEY_NOT_EQUAL.equals(testFlag.getQ())) {
            result.setApiCode("SUCCESS");
            result.setRealAmount(orderInfo.getAmount().add(new Money(1)));
        } else {
            result.setApiCode("unkown");
            result.setApiMessage("未知");
        }
    }
}
