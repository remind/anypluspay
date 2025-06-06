package com.anypluspay.payment.facade.acquiring.query;

import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.BaseResult;
import com.anypluspay.payment.domain.trade.acquiring.AcquiringOrder;
import com.anypluspay.payment.facade.acquiring.TradeResponse;

/**
 * @author wxj
 * 2025/5/18
 */
public class TradeResponseBuilder {

    public static TradeResponse build(AcquiringOrder order) {
        TradeResponse response = new TradeResponse();
        response.setSuccess(true);
        response.setTradeId(order.getTradeId());
        response.setRelationTradeId(order.getRelationTradeId());
        response.setTradeType(order.getTradeType().name());
        response.setOutTradeNo(order.getOutTradeNo());
        response.setPartnerId(order.getPartnerId());
        response.setPayeeId(order.getPayeeId());
        response.setPayeeAccountNo(order.getPayeeAccountNo());
        response.setPayerId(order.getPayerId());
        response.setSubject(order.getSubject());
        response.setAmount(order.getAmount());
        response.setExtension(order.getExtension());
        response.setStatus(order.getStatus().getCode());
        return response;
    }

    public static TradeResponse buildNotExists() {
        TradeResponse response = new TradeResponse();
        BaseResult.fillExceptionResult(response, new BizException("订单不存在"));
        return response;
    }
}
