package com.anypluspay.payment.application;

import com.anypluspay.payment.domain.pay.pay.PayOrderService;
import com.anypluspay.payment.domain.pay.refund.RefundOrderService;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
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
    protected PayOrderRepository payOrderRepository;

    @Autowired
    protected RefundOrderRepository refundOrderRepository;

    @Autowired
    protected PayOrderService payOrderService;

    @Autowired
    protected RefundOrderService refundOrderService;

}
