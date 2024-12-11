package com.anypluspay.channel.application.institution.gateway.advice;

import com.anypluspay.channel.application.institution.gateway.GatewayRequestAdvice;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.api.refund.RefundGatewayOrder;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/12/11
 */
@Service
public class RefundAdvice implements GatewayRequestAdvice<RefundGatewayOrder, GatewayResult> {
    @Override
    public void preHandle(ChannelApiContext channelApiContext, OrderContext orderContext, RefundGatewayOrder OrderInfo) {

    }

    @Override
    public void afterCompletion(ChannelApiContext channelApiContext, OrderContext orderContext, GatewayResult result) {
        InstOrder instOrder = orderContext.getInstOrder();
        instOrder.setInstResponseNo(result.getInstResponseNo());
    }

    @Override
    public ChannelApiType supportApiType() {
        return ChannelApiType.SINGLE_REFUND;
    }
}
