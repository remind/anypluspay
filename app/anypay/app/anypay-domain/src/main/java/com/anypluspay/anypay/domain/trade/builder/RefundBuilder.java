package com.anypluspay.anypay.domain.trade.builder;

import com.anypluspay.anypay.domain.common.service.IdGeneratorService;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.types.common.IdType;
import com.anypluspay.anypay.types.common.PayOrderStatus;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.MoneyUtils;
import com.anypluspay.commons.lang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wxj
 * 2026/2/2
 */
@Component
public class RefundBuilder {

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    public List<PayOrder> buildRefundPayOrder(TradeOrder refundTradeOrder, List<PayOrder> originPayOrders) {
        List<PayOrder> payOrders = new ArrayList<>();
        Map<String, List<PayOrder>> refundedPayOrders = payOrderRepository.loadByOrigPayIds(originPayOrders.stream().map(PayOrder::getPayId).toList())
                .stream().filter(payOrder -> payOrder.getStatus().equals(PayOrderStatus.SUCCESS))
                .collect(Collectors.groupingBy(PayOrder::getOrigPayId));
        Money remainRefundAmount = refundTradeOrder.getAmount();
        for (PayOrder originPayOrder : originPayOrders) {
            Money refundedAmount;
            if (refundedPayOrders.containsKey(originPayOrder.getPayId())) {
                refundedAmount = refundedPayOrders.get(originPayOrder.getPayId()).stream().map(PayOrder::getAmount).reduce(Money::add)
                        .orElse(new Money(0, originPayOrder.getAmount().getCurrency()));
            } else {
                refundedAmount = new Money(0, originPayOrder.getAmount().getCurrency());
            }
            Money refundEnableAmount = originPayOrder.getAmount().subtract(refundedAmount);
            if (MoneyUtils.isGreatThanZero(refundEnableAmount)) {
                if (MoneyUtils.isGreatEqual(remainRefundAmount, refundEnableAmount)) {
                    payOrders.add(buildRefundPayOrder(refundTradeOrder, originPayOrder, refundEnableAmount));
                    remainRefundAmount.subtractFrom(refundEnableAmount);
                } else {
                    payOrders.add(buildRefundPayOrder(refundTradeOrder, originPayOrder, remainRefundAmount));
                    remainRefundAmount.subtractFrom(remainRefundAmount);
                }
            }
            if (!MoneyUtils.isGreatThanZero(remainRefundAmount)) {
                break;
            }
        }
        Assert.isTrue(!MoneyUtils.isGreatThanZero(remainRefundAmount), "可退款金额不足");
        return payOrders;
    }

    private PayOrder buildRefundPayOrder(TradeOrder refundTradeOrder, PayOrder originPayOrder, Money refundAmount) {
        PayOrder payOrder = new PayOrder();
        payOrder.setTradeId(refundTradeOrder.getTradeId());
        payOrder.setPayId(idGeneratorService.genIdByRelateId(refundTradeOrder.getTradeId(), IdType.PAY_ORDER_ID));
        payOrder.setOrigPayId(originPayOrder.getPayId());
        payOrder.setPayMethod(originPayOrder.getPayMethod());
        payOrder.setAmount(refundAmount);
        payOrder.setStatus(PayOrderStatus.INIT);
        payOrder.setChannelCode(originPayOrder.getChannelCode());
        payOrder.setChannelRequestNo(StringUtil.randomId());
        return payOrder;
    }
}
