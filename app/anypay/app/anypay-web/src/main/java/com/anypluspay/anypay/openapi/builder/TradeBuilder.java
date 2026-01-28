package com.anypluspay.anypay.openapi.builder;

import com.anypluspay.anypay.domain.common.service.IdGeneratorService;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.types.common.IdType;
import com.anypluspay.anypay.openapi.request.InstantCreateOrderRequest;
import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.anypay.types.trade.TraderOrderStatus;
import com.anypluspay.commons.lang.types.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Currency;

/**
 * @author wxj
 * 2026/1/28
 */
@Service
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
        tradeOrder.setStatus(TraderOrderStatus.INIT);
        tradeOrder.setGmtExpire(LocalDateTime.now().plusMinutes(30));
        return tradeOrder;
    }
}