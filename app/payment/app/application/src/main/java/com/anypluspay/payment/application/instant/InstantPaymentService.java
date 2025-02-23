package com.anypluspay.payment.application.instant;

import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.domain.payorder.general.GeneralPayService;
import com.anypluspay.payment.types.PayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wxj
 * 2024/1/17
 */
@Service
public class InstantPaymentService extends AbstractPaymentService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private GeneralPayService generalPayService;

    public PayResult pay(Payment payment, GeneralPayOrder payOrder) {
        transactionTemplate.executeWithoutResult(status -> {
            paymentRepository.store(payment);
            generalPayOrderRepository.store(payOrder);
        });
        return generalPayService.process(payOrder);
    }

}
