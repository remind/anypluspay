package com.anypluspay.payment.application;

import com.anypluspay.payment.domain.payorder.general.GeneralPayService;
import com.anypluspay.payment.domain.payorder.refund.RefundService;
import com.anypluspay.payment.domain.repository.GeneralPayOrderRepository;
import com.anypluspay.payment.domain.repository.PaymentRepository;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wxj
 * 2024/1/26
 */
public abstract class AbstractPaymentService {

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected PaymentRepository paymentRepository;

    @Autowired
    protected GeneralPayOrderRepository generalPayOrderRepository;

    @Autowired
    protected RefundOrderRepository refundOrderRepository;

    @Autowired
    protected GeneralPayService generalPayService;

    @Autowired
    protected RefundService refundService;

}
