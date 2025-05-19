package com.anypluspay.payment.domain.biz.acquiring;

import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.types.status.TradeOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void processResult(String paymentId, boolean success) {
        transactionTemplate.executeWithoutResult(status -> {
            AcquiringOrder acquiringOrder = acquiringOrderRepository.lock(paymentId);
            if (success) {
                if (acquiringOrder.getStatus() == TradeOrderStatus.SUCCESS) {
                    // TODO 重复支付
                } else if (acquiringOrder.getStatus() == TradeOrderStatus.CLOSED) {
                    // TODO 关闭后支付
                } else {
                    acquiringOrder.setStatus(TradeOrderStatus.SUCCESS);
                }
            }
            acquiringOrderRepository.reStore(acquiringOrder);
        });
    }
}
