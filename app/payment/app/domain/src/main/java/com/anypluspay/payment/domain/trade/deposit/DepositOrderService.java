package com.anypluspay.payment.domain.trade.deposit;

import com.anypluspay.payment.domain.repository.DepositOrderRepository;
import com.anypluspay.payment.types.biz.DepositOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 充值服务
 * @author wxj
 * 2025/5/15
 */
@Service
public class DepositOrderService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DepositOrderRepository depositOrderRepository;

    public void processResult(String paymentId, boolean success) {
        transactionTemplate.executeWithoutResult(status -> {
            DepositOrder depositOrder = depositOrderRepository.lock(paymentId);
            if (depositOrder.getStatus() == DepositOrderStatus.PAYING) {
                if (success) {
                    depositOrder.setStatus(DepositOrderStatus.SUCCESS);
                } else {
                    depositOrder.setStatus(DepositOrderStatus.FAIL);
                }
                depositOrderRepository.reStore(depositOrder);
            }
        });
    }
}
