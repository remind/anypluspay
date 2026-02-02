package com.anypluspay.anypay.domain.trade.service;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.validator.RefundValidator;
import com.anypluspay.anypay.types.trade.TradeNotifyStatus;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
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

    public void refund(TradeOrder refundTradeOrder) {
        refundValidator.validate(refundTradeOrder);
    }
}
