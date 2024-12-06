package com.anypluspay.channel.application.institution.gateway.advice;

import com.anypluspay.channelgateway.api.sign.SignOrderInfo;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.application.institution.gateway.GatewayRequestAdvice;
import com.anypluspay.channel.types.channel.ChannelApiType;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/7/9
 */
@Service
public class SignRequestAdvice implements GatewayRequestAdvice<SignOrderInfo, SignResult> {

    @Override
    public void preHandle(ChannelApiContext channelApiContext, OrderContext orderContext, SignOrderInfo OrderInfo) {
        if (orderContext.getBizOrder() instanceof FundInOrder fundInOrder) {
            OrderInfo.setGoodsDesc(fundInOrder.getGoodsDesc());
        }
        OrderInfo.setCallbackServerUrl("http://www.baidu.com");
    }

    @Override
    public void afterCompletion(ChannelApiContext channelApiContext, OrderContext orderContext, SignResult result) {
        InstOrder instOrder = orderContext.getInstOrder();
        instOrder.setInstResponseNo(result.getInstResponseNo());
    }

    @Override
    public ChannelApiType supportApiType() {
        return ChannelApiType.SIGN;
    }

}
