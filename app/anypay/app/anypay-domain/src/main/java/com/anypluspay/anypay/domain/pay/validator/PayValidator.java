package com.anypluspay.anypay.domain.pay.validator;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.types.common.PayOrderStatus;
import com.anypluspay.commons.lang.types.Money;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author wxj
 * 2026/2/3
 */
@Component
@Slf4j
public class PayValidator {

    @Resource
    private PayOrderRepository payOrderRepository;

    public void refundValidate(TradeOrder refundTradeOrder, List<PayOrder> originPayOrders) {
        Assert.notEmpty(originPayOrders, "原支付单不能为空");
        originPayOrders.forEach(originPayOrder -> {
            Assert.isTrue(originPayOrder.getStatus() == PayOrderStatus.SUCCESS, "原支付单不是为成功");
        });
        Assert.isTrue(!refundTradeOrder.getAmount().greaterThan(
                originPayOrders.stream().map(PayOrder::getAmount).reduce(Money::add)
                        .orElse(new Money(0, refundTradeOrder.getAmount().getCurrency()))), "退款金额不能大于原支付金额");

    }
}
