package com.anypluspay.channelgateway.testbank;

import com.anypluspay.channelgateway.AbstractTestBank;
import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.api.query.QueryModel;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channelgateway.testbank.model.PayOrder;
import com.anypluspay.commons.lang.types.Money;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/12/3
 */
@Service
public class TestBankQueryGateway extends AbstractTestBank implements QueryGateway {
    @Override
    public void query(GatewayRequest<QueryModel> gatewayRequest, QueryModel normalContent, GatewayResult result) {
        PayOrder payOrder = getWebClient().get().uri("/online-bank/query?outTradeNo=" + normalContent.getInstRequestNo())
                .retrieve()
                .bodyToMono(PayOrder.class)
                .block();
        result.setSuccess(true);
        assert payOrder != null;
        result.setApiCode(payOrder.getStatus());
        result.setRealAmount(new Money(payOrder.getAmount()));
    }
}
