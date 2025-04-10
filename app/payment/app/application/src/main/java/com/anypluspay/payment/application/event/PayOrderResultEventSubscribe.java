package com.anypluspay.payment.application.event;

import com.anypluspay.payment.application.message.PayOrderResultProducer;
import com.anypluspay.payment.domain.payorder.event.PayOrderResultEvent;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.types.message.PayOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 支付订单结果事件订阅
 *
 * @author wxj
 * 2025/4/10
 */
@Slf4j
@Component
public class PayOrderResultEventSubscribe {

    @Autowired
    private PayOrderResultProducer payOrderResultProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PayOrderResultEvent payOrderResultEvent) {
        GeneralPayOrder payOrder = payOrderResultEvent.getPayOrder();
        PayOrderResult result = new PayOrderResult();
        result.setPaymentId(payOrder.getPaymentId());
        result.setOrderId(payOrder.getOrderId());
        result.setRequestId(payOrder.getRequestId());
        result.setStatus(payOrder.getOrderStatus().getCode());
        result.setMessage(payOrder.getOrderStatus().getDisplayName());
        payOrderResultProducer.send(result);
    }
}
