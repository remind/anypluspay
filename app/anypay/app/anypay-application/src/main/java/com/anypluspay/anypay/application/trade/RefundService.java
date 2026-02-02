package com.anypluspay.anypay.application.trade;

import com.anypluspay.anypay.application.trade.request.RefundApplyRequest;
import com.anypluspay.anypay.domain.common.service.IdGeneratorService;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.types.common.IdType;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Money;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 退款服务
 *
 * @author wxj
 * 2026/1/31
 */
@Service
public class RefundService {

    @Resource
    private TradeOrderRepository tradeOrderRepository;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private IdGeneratorService idGeneratorService;

    /**
     * 退款申请
     *
     * @param request
     */
    public void apply(RefundApplyRequest request) {
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder tradeOrder = tradeOrderRepository.validateAndLock(request.getOrigTradeId(), request.getOrigOutTradeNo());
            Money refundApplyAmount = new Money(request.getRefundAmount(), tradeOrder.getAmount().getCurrency());
            validate(tradeOrder, refundApplyAmount);
            saveRefundTradeOrder(tradeOrder, request.getRefundOutTradeNo(), refundApplyAmount);
        });
    }

    private void saveRefundTradeOrder(TradeOrder tradeOrder, String refundOutTradeNo, Money refundApplyAmount) {
        TradeOrder refundTradeOrder = new TradeOrder();
        refundTradeOrder.setTradeId(idGeneratorService.genIdByRelateId(tradeOrder.getTradeId(), IdType.TRADE_ORDER_ID));
        refundTradeOrder.setOutTradeNo(refundOutTradeNo);
        refundTradeOrder.setAmount(refundApplyAmount);
        refundTradeOrder.setTradeType(TradeType.REFUND_ACQUIRING);
        refundTradeOrder.setRelationTradeId(tradeOrder.getTradeId());
        refundTradeOrder.setPayerId(tradeOrder.getPayerId());
        refundTradeOrder.setPayeeId(tradeOrder.getPayeeId());
        refundTradeOrder.setStatus(TradeOrderStatus.WAIT_PAY);
        refundTradeOrder.setGmtExpire(LocalDateTime.now().plusMinutes(30));
        tradeOrderRepository.store(refundTradeOrder);
    }

    private void validate(TradeOrder tradeOrder, Money refundApplyAmount) {
        Assert.isTrue(tradeOrder.getStatus() == TradeOrderStatus.SUCCESS, "订单未支付");
        List<TradeOrder> refundTradeOrders = tradeOrderRepository.loadByRelationTradeId(tradeOrder.getTradeId());
        Money refundAmount = refundTradeOrders.stream()
                .filter(rto -> rto.getStatus() == TradeOrderStatus.SUCCESS && rto.getTradeType() == TradeType.REFUND_ACQUIRING)
                .map(TradeOrder::getAmount)
                .reduce(Money::add).orElse(new Money(0));
        if (refundApplyAmount.add(refundAmount).greaterThan(tradeOrder.getAmount())) {
            throw new BizException("退款金额已经超过可退金额");
        }
    }
}
