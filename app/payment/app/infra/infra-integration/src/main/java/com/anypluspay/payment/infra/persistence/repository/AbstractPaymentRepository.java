package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.BasePayment;
import com.anypluspay.payment.infra.persistence.dataobject.PaymentDO;
import com.anypluspay.payment.infra.persistence.mapper.PaymentMapper;
import com.anypluspay.payment.infra.persistence.convertor.PaymentDalConvertor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/1/18
 */
public abstract class AbstractPaymentRepository {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentDalConvertor paymentDalConvertor;

    protected BasePayment loadPayment(String paymentId, boolean lock) {
        PaymentDO paymentDO;
        if (lock) {
            paymentDO = paymentMapper.lockById(paymentId);
        } else {
            paymentDO = paymentMapper.selectById(paymentId);
        }
        return paymentDalConvertor.toPayment(paymentDO);
    }

    protected boolean storePayment(BasePayment payment) {
        PaymentDO paymentDO = paymentDalConvertor.toDO(payment);
        return paymentMapper.insert(paymentDO) == 1;
    }

}
