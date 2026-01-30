package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.openapi.builder.TradeBuilder;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

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

    protected void innerClose(String tradeId) {
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder tradeOrder = tradeOrderRepository.lock(tradeId);
            close(tradeOrder);
        });
    }

    protected void innerCloseByOutTradeNo(String outTradeNo) {
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder tradeOrder = tradeOrderRepository.lockByOutTradeNo(outTradeNo);
            close(tradeOrder);
        });
    }

    private void close(TradeOrder tradeOrder) {
        Assert.isTrue(tradeOrder != null, "交易单不存在");
        Assert.isTrue(tradeOrder.getStatus() == TradeOrderStatus.WAIT_PAY, "只有待支付的才能关闭");
        tradeOrder.setStatus(TradeOrderStatus.CLOSED);
        tradeOrderRepository.reStore(tradeOrder);
    }

}
