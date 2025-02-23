package com.anypluspay.payment.application.instant;

import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.payorder.GeneralPayOrder;
import com.anypluspay.payment.domain.payorder.PayService;
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
    private PayService payService;

    public PayResult pay(Payment payment, GeneralPayOrder payOrder) {
        transactionTemplate.executeWithoutResult(status -> {
            paymentRepository.store(payment);
            generalPayOrderRepository.store(payOrder);
        });
        return payService.process(payOrder);
    }

}
