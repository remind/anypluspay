package com.anypluspay.payment.application;

import com.anypluspay.payment.domain.BasePayment;
import com.anypluspay.payment.domain.payorder.PayOrder;
import com.anypluspay.payment.domain.payorder.service.PayOrderDomainService;
import com.anypluspay.payment.types.PayResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/1/26
 */
public abstract class AbstractPaymentService {

    @Autowired
    private PayOrderDomainService payOrderDomainService;

    protected PayResult pay(BasePayment payment, PayOrder payOrder) {
        return payOrderDomainService.pay(payOrder);
    }

}
