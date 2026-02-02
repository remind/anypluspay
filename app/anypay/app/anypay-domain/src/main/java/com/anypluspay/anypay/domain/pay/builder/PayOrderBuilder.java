package com.anypluspay.anypay.domain.pay.builder;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wxj
 * 2026/2/2
 */
@Component
public class PayOrderBuilder {

    @Autowired
    private PayOrderRepository payOrderRepository;

    public PayOrder buildByRefund(TradeOrder refundTradeOrder, TradeOrder originTradeOrder) {
        List<PayOrder> payOrders = payOrderRepository.loadByTradeId(originTradeOrder.getTradeId());
        PayOrder payOrder = new PayOrder();
        payOrder.setTradeId(refundTradeOrder.getTradeId());

        return payOrder;
    }
}
