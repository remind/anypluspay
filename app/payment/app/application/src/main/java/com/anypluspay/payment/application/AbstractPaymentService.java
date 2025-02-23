package com.anypluspay.payment.application;

import com.anypluspay.payment.domain.payorder.PayService;
import com.anypluspay.payment.domain.refund.RefundService;
import com.anypluspay.payment.domain.repository.GeneralPayOrderRepository;
import com.anypluspay.payment.domain.repository.PaymentRepository;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
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
    protected IdGeneratorService idGeneratorService;

    @Autowired
    protected PayService payService;

    @Autowired
    protected RefundService refundService;

}
