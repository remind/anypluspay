package com.anypluspay.payment.domain.deposit;

import com.anypluspay.payment.domain.repository.DepositOrderRepository;
import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 充值服务
 * @author wxj
 * 2025/5/15
 */
@Service
public class DepositService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DepositOrderRepository depositOrderRepository;

    public void processResult(String depositOrderId, GeneralPayOrderStatus payOrderStatus) {
        transactionTemplate.executeWithoutResult(status -> {
            DepositOrder depositOrder = depositOrderRepository.lock(depositOrderId);
            if (depositOrder.getStatus() == DepositOrderStatus.PAYING) {
                if (payOrderStatus == GeneralPayOrderStatus.SUCCESS) {
                    depositOrder.setStatus(DepositOrderStatus.SUCCESS);
                } else if (payOrderStatus == GeneralPayOrderStatus.FAIL) {
                    depositOrder.setStatus(DepositOrderStatus.FAIL);
                }
                depositOrderRepository.reStore(depositOrder);
            }
        });
    }
}
