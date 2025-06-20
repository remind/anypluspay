package com.anypluspay.payment.domain.trade.acquiring;

import com.anypluspay.payment.domain.pay.refund.RefundApplyService;
import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.types.trade.AcquiringOrderStatus;
import com.anypluspay.payment.types.pay.RefundType;
import com.anypluspay.payment.types.trade.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/5/18
 */
@Service
public class AcquiringOrderService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private AcquiringOrderRepository acquiringOrderRepository;

    @Autowired
    @Lazy
    private RefundApplyService refundApplyService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void processResult(String tradeId, String orderId, boolean success) {
        transactionTemplate.executeWithoutResult(status -> {
            AcquiringOrder acquiringOrder = acquiringOrderRepository.lock(tradeId);
            if (success) {
                if (acquiringOrder.getStatus() == AcquiringOrderStatus.SUCCESS) {
                    if (acquiringOrder.getTradeType() == TradeType.INSTANT_ACQUIRING || acquiringOrder.getTradeType() == TradeType.ENSURE_ACQUIRING) {
                        refund(tradeId, orderId, RefundType.REPEAT);
                    }
                } else if (acquiringOrder.getStatus() == AcquiringOrderStatus.CLOSED) {
                    if (acquiringOrder.getTradeType() == TradeType.INSTANT_ACQUIRING || acquiringOrder.getTradeType() == TradeType.ENSURE_ACQUIRING) {
                        refund(tradeId, orderId, RefundType.ORDER_CLOSE);
                    }
                } else {
                    if (acquiringOrder.getGmtExpire() != null && LocalDateTime.now().isAfter(acquiringOrder.getGmtExpire())) {
                        if (acquiringOrder.getTradeType() == TradeType.INSTANT_ACQUIRING || acquiringOrder.getTradeType() == TradeType.ENSURE_ACQUIRING) {
                            acquiringOrder.setStatus(AcquiringOrderStatus.CLOSED);
                            refund(tradeId, orderId, RefundType.ORDER_CLOSE);
                        }
                    } else {
                        acquiringOrder.setOrderId(orderId);
                        acquiringOrder.setStatus(AcquiringOrderStatus.SUCCESS);
                    }
                }
            }
            acquiringOrderRepository.reStore(acquiringOrder);
        });
    }

    private void refund(String tradeId, String orderId, RefundType refundType) {
        threadPoolTaskExecutor.execute(() -> refundApplyService.apply(tradeId, orderId, refundType));
    }
}
