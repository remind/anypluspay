package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.openapi.builder.TradeBuilder;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wxj
 * 2026/1/27
 */
public class AbstractTradeController {

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    protected TradeBuilder tradeBuilder;

    @Resource
    protected TradeOrderRepository tradeOrderRepository;

    protected void save(TradeOrder tradeOrder) {
        transactionTemplate.executeWithoutResult(status -> {
            tradeOrderRepository.store(tradeOrder);
        });
    }

}
