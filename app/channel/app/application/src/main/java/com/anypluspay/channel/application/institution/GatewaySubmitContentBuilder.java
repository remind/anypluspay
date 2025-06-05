package com.anypluspay.channel.application.institution;

import com.anypluspay.channel.application.institution.gateway.GatewayRequestAdvice;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.request.NormalContent;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.types.RequestResponseClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 网关提交内容构造器
 *
 * @author wxj
 * 2024/12/18
 */
@Service
@Slf4j
public class GatewaySubmitContentBuilder {

    @Autowired
    private CombineCallbackUrlService combineCallbackUrlService;

    @Qualifier("gatewayInterceptorMap")
    @Autowired
    private Map<ChannelApiType, GatewayRequestAdvice> gatewayInterceptorMap;

    /**
     * 构造请求内容
     *
     * @param channelApiContext
     * @param orderContext
     * @return
     */
    @SuppressWarnings("unchecked")
    public RequestContent buildRequestContent(ChannelApiContext channelApiContext, OrderContext orderContext) {
        try {
            Class<?> cl = RequestResponseClass.getRequestClass(channelApiContext.getChannelApiType());
            RequestContent requestContent = (RequestContent) cl.getConstructor().newInstance();
            if (requestContent instanceof NormalContent normalContent) {
                normalContent.setExtension(orderContext.getInstOrder().getRequestExt());
                normalContent.setInstOrderId(orderContext.getInstOrder().getInstOrderId());
                normalContent.setInstRequestNo(orderContext.getInstOrder().getInstRequestNo());
                normalContent.setServerNotifyUrl(combineCallbackUrlService.getServerNotifyUrl(channelApiContext, orderContext.getInstOrder().getInstRequestNo()));
                fillBizOrderInfo(normalContent, orderContext.getBizOrder());
            }
            if (gatewayInterceptorMap.get(channelApiContext.getChannelApiType()) != null) {
                gatewayInterceptorMap.get(channelApiContext.getChannelApiType()).preHandle(channelApiContext, orderContext, requestContent);
            }
            requestContent.setApiParamId(orderContext.getInstOrder().getApiParamId());
            return requestContent;
        } catch (Exception e) {
            log.error("构造请求订单参数异常", e);
            throw new RuntimeException(e);
        }
    }

    private void fillBizOrderInfo(NormalContent normalContent, BaseBizOrder bizOrder) {
        if (bizOrder instanceof FundInOrder fundOrder) {
            normalContent.setTargetInst(fundOrder.getPayInst());
            normalContent.setAmount(fundOrder.getAmount());
        } else if (bizOrder instanceof RefundOrder refundOrder) {
            normalContent.setAmount(refundOrder.getAmount());
        }
    }
}
