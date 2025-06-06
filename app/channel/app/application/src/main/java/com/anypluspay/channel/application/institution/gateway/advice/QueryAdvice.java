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
import com.anypluspay.channelgateway.api.query.QueryModel;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wxj
 * 2025/6/6
 */
@Service
public class QueryAdvice implements GatewayRequestAdvice<QueryModel, GatewayResult> {

    @Autowired
    protected InstOrderRepository instOrderRepository;

    @Autowired
    protected BizOrderRepository bizOrderRepository;

    @Override
    public void preHandle(ChannelApiContext channelApiContext, OrderContext orderContext, QueryModel requestContent) {
        if (orderContext.getBizOrder() instanceof RefundOrder refundOrder) {
            FundInOrder fundOrder = (FundInOrder) bizOrderRepository.load(refundOrder.getOrigOrderId());
            InstOrder origInstOrder = instOrderRepository.load(fundOrder.getInstOrderId());
            requestContent.setOrigInstRequestNo(origInstOrder.getInstRequestNo());
            requestContent.setOrigInstResponseNo(origInstOrder.getInstResponseNo());
        }
    }

    @Override
    public void afterCompletion(ChannelApiContext channelApiContext, OrderContext orderContext, GatewayResult result) {

    }

    @Override
    public List<ChannelApiType> supportApiType() {
        return List.of(ChannelApiType.SINGLE_QUERY, ChannelApiType.SINGLE_REFUND_QUERY);
    }
}
