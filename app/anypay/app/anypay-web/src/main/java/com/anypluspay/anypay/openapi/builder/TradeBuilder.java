package com.anypluspay.anypay.openapi.builder;

import com.anypluspay.anypay.domain.common.service.IdGeneratorService;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.openapi.request.InstantCreateOrderRequest;
import com.anypluspay.anypay.openapi.response.TradeOrderResponse;
import com.anypluspay.anypay.types.common.IdType;
import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import com.anypluspay.commons.lang.types.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Currency;

/**
 * @author wxj
 * 2026/1/28
 */
@Component
public class TradeBuilder {

    @Autowired
    private IdGeneratorService idGeneratorService;

    public TradeOrder buildTradeOrder(InstantCreateOrderRequest request, TradeType tradeType) {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setTradeId(idGeneratorService.genTradeId(request.getPayerId(), IdType.TRADE_ORDER_ID));
        tradeOrder.setTradeType(tradeType);
        tradeOrder.setOutTradeNo(request.getOutTradeNo());
        tradeOrder.setPayerId(request.getPayerId());
        tradeOrder.setPayeeId(request.getPayeeId());
        tradeOrder.setPayeeAccountNo(request.getPayeeAccountNo());
        tradeOrder.setSubject(request.getSubject());
        tradeOrder.setBody(request.getBody());
        tradeOrder.setAmount(new Money(request.getAmount(), Currency.getInstance(request.getCurrency())));
        tradeOrder.setPayeeId(request.getPayeeId());
        tradeOrder.setStatus(TradeOrderStatus.WAIT_PAY);
        tradeOrder.setGmtExpire(LocalDateTime.now().plusMinutes(30));
        return tradeOrder;
    }

    public TradeOrderResponse buildTradeOrderResponse(TradeOrder tradeOrder) {
        TradeOrderResponse response = new TradeOrderResponse();
        response.setTradeId(tradeOrder.getTradeId());
        response.setOutTradeNo(tradeOrder.getOutTradeNo());
        response.setTradeType(tradeOrder.getTradeType().getCode());
        response.setAmount(tradeOrder.getAmount().toString());
        response.setCurrency(tradeOrder.getAmount().getCurrency().getCurrencyCode());
        response.setStatus(tradeOrder.getStatus().getCode());
        return response;
    }
}