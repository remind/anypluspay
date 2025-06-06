package com.anypluspay.channel.application.institution.gateway;

import com.anypluspay.channel.application.institution.InstResultService;
import com.anypluspay.channel.application.route.ChannelRouteService;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstCommandOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author wxj
 * 2024/12/15
 */
@Service
public class GatewayResultHandler {

    @Autowired
    private ChannelRouteService channelRouteService;

    @Autowired
    private BizOrderRepository bizOrderRepository;

    @Autowired
    private InstOrderRepository instOrderRepository;

    @Autowired
    private InstCommandOrderRepository instCommandOrderRepository;

    @Autowired
    private InstResultService instResultService;

    @Qualifier("gatewayInterceptorMap")
    @Autowired
    private Map<ChannelApiType, GatewayRequestAdvice> gatewayInterceptorMap;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public OrderContext handle(GatewayResult gatewayResult, Long commandId) {
        ChannelApiContext channelApiContext = channelRouteService.routeByChannel(gatewayResult.getChannelCode(), gatewayResult.getApiType());
        return transactionTemplate.execute(status -> {
            BaseBizOrder bizOrder = bizOrderRepository.lock(getBizOrderId(commandId));
            OrderContext orderContext = getOrderContext(bizOrder, commandId);
            if (gatewayResult.isSuccess()) {
                preProcess(channelApiContext, orderContext, gatewayResult);
            }
            instResultService.process(channelApiContext, orderContext, gatewayResult);
            return orderContext;
        });
    }

    private String getBizOrderId(Long commandId) {
        InstCommandOrder instCommandOrder = instCommandOrderRepository.load(commandId);
        InstOrder instOrder = instOrderRepository.load(instCommandOrder.getInstOrderId());
        return instOrder.getBizOrderId();
    }

    private OrderContext getOrderContext(BaseBizOrder bizOrder, Long instCommandOrderId) {
        InstCommandOrder instCommandOrder = instCommandOrderRepository.load(instCommandOrderId);
        InstOrder instOrder = instOrderRepository.load(instCommandOrder.getInstOrderId());
        return new OrderContext(bizOrder, instOrder, instCommandOrder);
    }

    /**
     * 前置预处理
     *
     * @param channelApiContext
     * @param orderContext
     * @param gatewayResult
     */
    @SuppressWarnings("unchecked")
    public void preProcess(ChannelApiContext channelApiContext, OrderContext orderContext, GatewayResult gatewayResult) {
        gatewayResult.setReceiveTime(LocalDateTime.now());

        if (gatewayResult.isSuccess() && orderContext.getInstOrder() != null) {
            orderContext.getInstOrder().getResponseExt().addAll(gatewayResult.getResponseExt());
        }

        // 各ApiType个性化处理
        if (gatewayInterceptorMap.get(channelApiContext.getChannelApiType()) != null) {
            gatewayInterceptorMap.get(channelApiContext.getChannelApiType()).afterCompletion(channelApiContext, orderContext, gatewayResult);
        }
    }

}
