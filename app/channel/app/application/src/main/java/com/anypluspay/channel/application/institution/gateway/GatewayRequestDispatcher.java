package com.anypluspay.channel.application.institution.gateway;

import com.anypluspay.channelgateway.request.StringInfo;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channelgateway.request.OrderInfo;
import com.anypluspay.channelgateway.types.RequestResponseClass;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.result.CsResultCode;
import com.anypluspay.commons.lang.utils.ExtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 网关分发器
 *
 * @author wxj
 * 2024/7/9
 */
@Service
@Slf4j
public class GatewayRequestDispatcher {

    @Autowired
    private GatewayProxy gatewayProxy;

    @Qualifier("gatewayInterceptorMap")
    @Autowired
    private Map<ChannelApiType, GatewayRequestAdvice> gatewayInterceptorMap;

    /**
     * 执行分发
     *
     * @param channelApiContext
     * @param orderContext
     * @return
     */
    public GatewayResult doDispatch(ChannelApiContext channelApiContext, OrderContext orderContext) {
        OrderInfo orderInfo = buildRequestOrder(channelApiContext, orderContext);
        GatewayResult gatewayResult;
        int retryCount = 0;
        boolean afterProcess = false;
        do {
            try {
                gatewayResult = gatewayProxy.invoke(channelApiContext.getChannelApi(), orderInfo);
                afterProcess = true;
            } catch (Exception e) {
                log.error("网关分发异常", e);
                gatewayResult = buildInvokeFailResult();
            }
            retryCount++;
        } while (canRetry(channelApiContext.getChannelApi(), gatewayResult, retryCount));
        if (afterProcess) {
            // 只有在调用渠道成功之后才会做相关处理
            processResult(channelApiContext, orderContext, gatewayResult);
        }
        return gatewayResult;
    }

    /**
     * 执行无订单分发，如通知类结果处理
     *
     * @param channelApiContext
     * @param requestBody
     * @return
     */
    public GatewayResult doNoneOrderDispatch(ChannelApiContext channelApiContext, String requestBody) {
        GatewayResult gatewayResult;
        try {
            gatewayResult = gatewayProxy.invoke(channelApiContext.getChannelApi(), new StringInfo(requestBody));
        } catch (Exception e) {
            log.error("网关分发异常", e);
            gatewayResult = buildInvokeFailResult();
        }
        return gatewayResult;
    }

    /**
     * 处理结果，对instOrder可进行修改
     *
     * @param channelApiContext
     * @param orderContext
     * @param gatewayResult
     * @return
     */
    @SuppressWarnings("unchecked")
    public void processResult(ChannelApiContext channelApiContext, OrderContext orderContext, GatewayResult gatewayResult) {
        gatewayResult.setReceiveTime(LocalDateTime.now());

        if (gatewayResult.isSuccess() && orderContext.getInstOrder() != null) {
            orderContext.getInstOrder().setResponseExtra(ExtUtil.merge(orderContext.getInstOrder().getResponseExtra(), gatewayResult.getResponseExtra()));
        }

        // 各ApiType个性化处理
        if (gatewayInterceptorMap.get(channelApiContext.getChannelApiType()) != null) {
            gatewayInterceptorMap.get(channelApiContext.getChannelApiType()).afterCompletion(channelApiContext, orderContext, gatewayResult);
        }
    }

    /**
     * 是否可重试
     *
     * @param channelApi
     * @param gatewayResult
     * @param retryCount
     * @return
     */
    private boolean canRetry(ChannelApi channelApi, GatewayResult gatewayResult, int retryCount) {
        return !gatewayResult.isSuccess() && retryCount < 3 && channelApi.getType() == ChannelApiType.SIGN;
    }

    /**
     * 构造失败的执行器调用结果
     *
     * @return
     */
    private GatewayResult buildInvokeFailResult() {
        GatewayResult gatewayResult = new GatewayResult();
        gatewayResult.setSuccess(false);
        gatewayResult.setApiCode(CsResultCode.INVOKE_FAIL.getCode());
        gatewayResult.setApiMessage(CsResultCode.INVOKE_FAIL.getMessage());
        gatewayResult.setReceiveTime(LocalDateTime.now());
        return gatewayResult;
    }

    @SuppressWarnings("unchecked")
    private OrderInfo buildRequestOrder(ChannelApiContext channelApiContext, OrderContext orderContext) {
        try {
            Class<?> cl = RequestResponseClass.getRequestClass(channelApiContext.getChannelApiType());
            OrderInfo orderInfo = (OrderInfo) cl.getConstructor().newInstance();
            orderInfo.setExtra(orderContext.getInstOrder().getRequestExtra());
            orderInfo.setInstOrderId(orderContext.getInstOrder().getInstOrderId());
            orderInfo.setInstRequestNo(orderContext.getInstOrder().getInstRequestNo());

            fillBizOrderInfo(orderInfo, orderContext.getBizOrder());

            if (gatewayInterceptorMap.get(channelApiContext.getChannelApiType()) != null) {
                gatewayInterceptorMap.get(channelApiContext.getChannelApiType()).preHandle(channelApiContext, orderContext, orderInfo);
            }
            return orderInfo;
        } catch (Exception e) {
            log.error("构造请求订单参数异常", e);
            throw new RuntimeException(e);
        }
    }

    private void fillBizOrderInfo(OrderInfo orderInfo, BaseBizOrder bizOrder) {
        if (bizOrder instanceof FundInOrder fundOrder) {
            orderInfo.setTargetInst(fundOrder.getPayInst());
            orderInfo.setAmount(fundOrder.getAmount());
        }
    }
}
