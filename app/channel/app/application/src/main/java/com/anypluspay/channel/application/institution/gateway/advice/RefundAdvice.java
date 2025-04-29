package com.anypluspay.channel.application.institution.gateway.advice;

import com.anypluspay.channel.application.institution.gateway.GatewayRequestAdvice;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.api.refund.RefundContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/12/11
 */
@Service
public class RefundAdvice implements GatewayRequestAdvice<RefundContent, GatewayResult> {

    @Autowired
    protected InstOrderRepository instOrderRepository;

    @Autowired
    protected BizOrderRepository bizOrderRepository;

    @Override
    public void preHandle(ChannelApiContext channelApiContext, OrderContext orderContext, RefundContent requestContent) {
        RefundOrder refundOrder = (RefundOrder) orderContext.getBizOrder();
        FundInOrder fundOrder = (FundInOrder) bizOrderRepository.load(refundOrder.getOrigOrderId());
        InstOrder origInstOrder = instOrderRepository.load(fundOrder.getInstOrderId());
        requestContent.setOrigInstRequestNo(origInstOrder.getInstRequestNo());
        requestContent.setOrigInstResponseNo(origInstOrder.getInstResponseNo());
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
