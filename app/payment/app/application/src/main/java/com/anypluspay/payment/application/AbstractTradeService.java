package com.anypluspay.payment.application;

import com.anypluspay.payment.domain.pay.pay.PayOrderService;
import com.anypluspay.payment.domain.pay.refund.RefundOrderService;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 交易业务服务抽象类
 *
 * @author wxj
 * 2024/1/26
 */
public abstract class AbstractTradeService {

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected PayOrderRepository payOrderRepository;

    @Autowired
    protected RefundOrderRepository refundOrderRepository;

    @Autowired
    protected PayOrderService payOrderService;

    @Autowired
    protected RefundOrderService refundOrderService;

}
