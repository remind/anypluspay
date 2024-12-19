package com.anypluspay.channelgateway.test.fo;

import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.NormalContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channelgateway.test.AbstractLocalBankGateway;
import com.anypluspay.commons.lang.types.Money;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/7/11
 */
@Service
public class LocalBankFundOutQueryGateway extends AbstractLocalBankGateway implements QueryGateway {
    @Override
    public void query(GatewayRequest<NormalContent> gatewayRequest, NormalContent normalContent, GatewayResult result) {
        result.setSuccess(true);
        if (isTest(normalContent)) {
            testProcess(normalContent, result);
        }
    }

    private void testProcess(NormalContent normalContent, GatewayResult result) {
        TestFlag testFlag = getTestFlag(normalContent);
        if (TestConstants.S.equals(testFlag.getQ())) {
            result.setApiCode("SUCCESS");
        } else if (TestConstants.F.equals(testFlag.getQ())) {
            result.setApiCode("ORDER_CLOSED");
            result.setApiMessage("订单已关闭");
        } else if (TestConstants.QUERY_MONEY_NOT_EQUAL.equals(testFlag.getQ())) {
            result.setApiCode("SUCCESS");
            result.setRealAmount(normalContent.getAmount().add(new Money(1)));
        } else {
            result.setApiCode("unkown");
            result.setApiMessage("未知");
        }
    }
}
