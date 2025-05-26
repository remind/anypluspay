package com.anypluspay.payment.domain.biz.acquiring;

import com.anypluspay.payment.domain.process.refund.RefundApplyService;
import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import com.anypluspay.payment.types.pay.RefundType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

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

    public void processResult(String paymentId, String orderId, boolean success) {
        transactionTemplate.executeWithoutResult(status -> {
            AcquiringOrder acquiringOrder = acquiringOrderRepository.lock(paymentId);
            if (success) {
                if (acquiringOrder.getStatus() == AcquiringOrderStatus.SUCCESS) {
                    threadPoolTaskExecutor.execute(() -> refundApplyService.apply(paymentId, orderId, RefundType.REPEAT));
                } else if (acquiringOrder.getStatus() == AcquiringOrderStatus.CLOSED) {
                    threadPoolTaskExecutor.execute(() -> refundApplyService.apply(paymentId, orderId, RefundType.ORDER_CLOSE));
                } else {
                    acquiringOrder.setPayOrderId(orderId);
                    acquiringOrder.setStatus(AcquiringOrderStatus.SUCCESS);
                }
            }
            acquiringOrderRepository.reStore(acquiringOrder);
        });
    }
}
