package com.anypluspay.anypay.domain.trade.service;

import com.anypluspay.anypay.domain.channel.spi.response.ChannelUnifiedOrderResponse;
import com.anypluspay.anypay.domain.pay.PayMethod;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.pay.service.PayOrderDomainService;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.domain.trade.validator.RefundValidator;
import com.anypluspay.anypay.types.trade.TradeNotifyStatus;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

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
    private TradeOrderRepository tradeOrderRepository;

    @Resource
    private PayOrderRepository payOrderRepository;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private PayOrderDomainService payOrderDomainService;

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
            payOrderDomainService.pay(tradeOrder, payOrder);

        });
    }

    public void refund(TradeOrder refundTradeOrder) {
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder originTradeOrder = tradeOrderRepository.lock(refundTradeOrder.getRelationTradeId());
            refundValidator.validate(refundTradeOrder, originTradeOrder);
            tradeOrderRepository.store(refundTradeOrder);
        });
    }
}
