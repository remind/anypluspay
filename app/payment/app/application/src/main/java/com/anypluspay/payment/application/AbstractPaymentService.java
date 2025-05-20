package com.anypluspay.payment.application;

import com.anypluspay.payment.domain.process.ProcessProcessService;
import com.anypluspay.payment.domain.process.refund.RefundService;
import com.anypluspay.payment.domain.repository.PayProcessRepository;
import com.anypluspay.payment.domain.repository.PaymentRepository;
import com.anypluspay.payment.domain.repository.RefundProcessRepository;
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
    protected PayProcessRepository payProcessRepository;

    @Autowired
    protected RefundProcessRepository refundProcessRepository;

    @Autowired
    protected ProcessProcessService payProcessService;

    @Autowired
    protected RefundService refundService;

}
