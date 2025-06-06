package com.anypluspay.channel.application.institution.gateway.advice;

import com.anypluspay.channel.application.institution.gateway.GatewayRequestAdvice;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.bizorder.fund.FundOutOrder;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.request.FundOutContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wxj
 * 2024/12/18
 */
@Service
public class SingleFundOutAdvice implements GatewayRequestAdvice<FundOutContent, GatewayResult> {
    @Override
    public void preHandle(ChannelApiContext channelApiContext, OrderContext orderContext, FundOutContent requestContent) {
        FundOutOrder fundOutOrder = (FundOutOrder) orderContext.getBizOrder();
        requestContent.setAmount(fundOutOrder.getAmount());
        requestContent.setBankCode(fundOutOrder.getBankCode());
        requestContent.setAccountNo(fundOutOrder.getAccountNo());
        requestContent.setAccountName(fundOutOrder.getAccountName());
        requestContent.setAccountType(fundOutOrder.getAccountType());
        requestContent.setInstOrderId(orderContext.getInstOrder().getInstOrderId());
        requestContent.setInstRequestNo(orderContext.getInstOrder().getInstRequestNo());
        requestContent.setExtension(orderContext.getInstOrder().getRequestExt());
    }

    @Override
    public void afterCompletion(ChannelApiContext channelApiContext, OrderContext orderContext, GatewayResult result) {

    }

    @Override
    public List<ChannelApiType> supportApiType() {
        return List.of(ChannelApiType.SINGLE_FUND_OUT);
    }
}
