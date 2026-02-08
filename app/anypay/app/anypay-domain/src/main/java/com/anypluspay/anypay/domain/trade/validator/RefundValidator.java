package com.anypluspay.anypay.domain.trade.validator;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.MoneyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wxj
 * 2026/2/2
 */
@Component
public class RefundValidator {

    @Resource
    private TradeOrderRepository tradeOrderRepository;

    public void validate(TradeOrder refundTradeOrder, TradeOrder originTradeOrder ) {
        Assert.isTrue(MoneyUtils.isGreatThanZero(refundTradeOrder.getAmount()), "退款金额必须大于0");
        Assert.isTrue(originTradeOrder != null, "原交易不存在");
        validateOriginTradeOrder(originTradeOrder);
        validateRefundAmount(originTradeOrder, refundTradeOrder);
    }

    private void validateOriginTradeOrder(TradeOrder originTradeOrder) {
        Assert.isTrue(originTradeOrder.getStatus() == TradeOrderStatus.SUCCESS, "原交易只能是成功状态才能退款");
    }

    private void validateRefundAmount(TradeOrder originTradeOrder, TradeOrder refundTradeOrder) {
        Assert.isTrue(!refundTradeOrder.getAmount().greaterThan(originTradeOrder.getAmount()), "退款金额不能大于原交易订单金额");
        List<TradeOrder> refundTradeOrders = tradeOrderRepository.loadByRelationTradeId(originTradeOrder.getTradeId());
        if (!CollectionUtils.isEmpty(refundTradeOrders)) {
            Money refundAmount = refundTradeOrders.stream()
                    .filter(rto -> (rto.getStatus() == TradeOrderStatus.SUCCESS || rto.getStatus() == TradeOrderStatus.WAIT_PAY) && rto.getTradeType() == TradeType.REFUND_ACQUIRING)
                    .map(TradeOrder::getAmount)
                    .reduce(Money::add).orElse(new Money(0));
            Assert.isTrue(!refundTradeOrder.getAmount().add(refundAmount).greaterThan(originTradeOrder.getAmount()), "退款金额已经超过可退金额");
        }
    }
}
