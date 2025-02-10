package com.anypluspay.payment.application.instant;

import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.domain.instant.InstantPayment;
import com.anypluspay.payment.domain.payorder.PayOrder;
import com.anypluspay.payment.domain.repository.InstantPaymentRepository;
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
    private InstantPaymentRepository instantPaymentRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public PayResult pay(InstantPayment payment) {
        return transactionTemplate.execute(status -> {
            instantPaymentRepository.store(payment);
            return pay(payment, (PayOrder) payment.getBasePayOrder());
        });
    }


}
