package com.anypluspay.channelgateway.testbank;

import com.anypluspay.channelgateway.AbstractTestBank;
import com.anypluspay.channelgateway.api.refund.RefundContent;
import com.anypluspay.channelgateway.api.refund.RefundGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channelgateway.testbank.model.RefundOrderResponse;
import com.anypluspay.commons.lang.types.Money;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxj
 * 2024/12/8
 */
@Service
public class TestBankRefundGateway extends AbstractTestBank implements RefundGateway {
    @Override
    public void refund(GatewayRequest<RefundContent> gatewayRequest, RefundContent refundOrder, GatewayResult result) {
        Map<String, String> params = new HashMap<>();
        params.put("outRequestNo", refundOrder.getInstRequestNo());
        params.put("origOrderId", refundOrder.getOrigInstResponseNo());
        params.put("amount", refundOrder.getAmount().getAmount().toString());
        params.put("reason", refundOrder.getReason());
        params.put("notifyUrl", refundOrder.getServerNotifyUrl());
        RefundOrderResponse response = webClient.post().uri("/online-bank/refund")
                .bodyValue(params)
                .retrieve()
                .bodyToMono(RefundOrderResponse.class)
                .block();
        result.setSuccess(true);
        assert response != null;
        result.setInstResponseNo(response.getId().toString());
        result.setApiCode(response.getStatus());
        result.setRealAmount(new Money(response.getAmount()));
    }
}
