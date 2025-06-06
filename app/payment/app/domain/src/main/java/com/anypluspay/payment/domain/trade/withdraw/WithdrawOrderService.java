package com.anypluspay.payment.domain.trade.withdraw;

import com.anypluspay.payment.domain.repository.WithdrawOrderRepository;
import com.anypluspay.payment.types.biz.WithdrawOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wxj
 * 2025/5/16
 */
@Service
public class WithdrawOrderService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private WithdrawOrderRepository withdrawOrderRepository;

    public void processResult(String paymentId, boolean success) {
        transactionTemplate.executeWithoutResult(status -> {
            WithdrawOrder withdrawOrder = withdrawOrderRepository.lock(paymentId);
            if (withdrawOrder.getStatus() == WithdrawOrderStatus.PAYING) {
                if (success) {
                    withdrawOrder.setStatus(WithdrawOrderStatus.SUCCESS);
                } else {
                    withdrawOrder.setStatus(WithdrawOrderStatus.FAIL);
                }
                withdrawOrderRepository.reStore(withdrawOrder);
            }
        });
    }
}
