package com.anypluspay.anypay.domain.trade.service;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.pay.service.PayOrderDomainService;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.builder.RefundBuilder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.domain.trade.validator.RefundValidator;
import com.anypluspay.anypay.types.PayResult;
import com.anypluspay.anypay.types.trade.TradeNotifyStatus;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 交易单领域服务
 *
 * @author wxj
 * 2026/1/30
 */
@Service
public class TradeOrderDomainService {

    @Resource
    private RefundValidator refundValidator;

    @Resource
    private RefundBuilder refundBuilder;

    @Resource
    private PayOrderRepository payOrderRepository;

    @Resource
    private TradeOrderRepository tradeOrderRepository;

    @Resource
    private PayOrderDomainService payOrderDomainService;

    @Resource
    private TransactionTemplate transactionTemplate;

    /**
     * 支付成功
     *
     * @param tradeOrder
     */
    public void paySuccess(TradeOrder tradeOrder) {
        Assert.isTrue(tradeOrder.getStatus() == TradeOrderStatus.WAIT_PAY, "仅支付中才能支付成功");
        tradeOrder.setStatus(TradeOrderStatus.SUCCESS);
        tradeOrder.setNotifyStatus(TradeNotifyStatus.WAIT_NOTIFY);
    }

    /**
     * 关闭订单
     *
     * @param tradeOrder
     */
    public void close(TradeOrder tradeOrder) {
        Assert.isTrue(tradeOrder.getStatus() == TradeOrderStatus.WAIT_PAY, "仅待支付才能关闭");
        tradeOrder.setStatus(TradeOrderStatus.CLOSED);
    }

    public void pay(String tradeId, PayOrder payOrder) {
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder tradeOrder = tradeOrderRepository.lock(tradeId);
            payOrderRepository.store(payOrder);
        });
    }

    public void refund(TradeOrder refundTradeOrder) {
        List<PayOrder> originPayOrders = payOrderRepository.loadByTradeId(refundTradeOrder.getRelationTradeId());
        AtomicReference<List<PayOrder>> refundPayOrderRef = new AtomicReference<>();
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder originTradeOrder = tradeOrderRepository.lock(refundTradeOrder.getRelationTradeId());

            refundValidator.validate(refundTradeOrder, originTradeOrder, originPayOrders);

            refundPayOrderRef.set(refundBuilder.buildRefundPayOrder(refundTradeOrder, originPayOrders));
            payOrderRepository.store(refundPayOrderRef.get());
            tradeOrderRepository.store(refundTradeOrder);
        });
        PayResult payResult = payOrderDomainService.refund(refundTradeOrder, refundPayOrderRef.get(), originPayOrders);
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder refundedTradeOrder = tradeOrderRepository.lock(refundTradeOrder.getTradeId());
            Assert.isTrue(refundedTradeOrder.getStatus() == TradeOrderStatus.WAIT_PAY, "仅退款处理中才能处理");
            refundedTradeOrder.setStatus(payResult.getStatus());
            refundedTradeOrder.setResultCode(payResult.getResultCode());
            refundedTradeOrder.setResultMsg(payResult.getResultMsg());
            tradeOrderRepository.store(refundTradeOrder);
        });
    }
}
