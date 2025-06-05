package com.anypluspay.channel.application.institution.gateway.advice;

import com.anypluspay.channel.application.context.ChannelContext;
import com.anypluspay.channel.application.institution.gateway.GatewayRequestAdvice;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.facade.request.NotifyRequest;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.api.verify.VerifyModel;
import com.anypluspay.channelgateway.api.verify.VerifySignResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 验签增强处理器
 *
 * @author wxj
 * 2024/12/9
 */
@Service
public class VerifySignAdvice implements GatewayRequestAdvice<VerifyModel, VerifySignResult> {
    @Override
    public void preHandle(ChannelApiContext channelApiContext, OrderContext orderContext, VerifyModel verifyModel) {
        NotifyRequest request = (NotifyRequest) ChannelContext.get();
        verifyModel.setRequestBody(request.getRequestBody());

        if (orderContext.getInstOrder() != null) {
            verifyModel.setInstRequestNo(orderContext.getInstOrder().getInstRequestNo());
        }
    }

    @Override
    public void afterCompletion(ChannelApiContext channelApiContext, OrderContext orderContext, VerifySignResult result) {
        InstOrder instOrder = orderContext.getInstOrder();
        if (StringUtils.isNotBlank(result.getInstResponseNo())) {
            instOrder.setInstResponseNo(result.getInstResponseNo());
        }
    }

    @Override
    public ChannelApiType supportApiType() {
        return ChannelApiType.VERIFY_SIGN;
    }
}
