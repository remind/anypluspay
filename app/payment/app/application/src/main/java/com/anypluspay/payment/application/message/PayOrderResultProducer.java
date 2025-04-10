package com.anypluspay.payment.application.message;

import com.anypluspay.payment.types.message.PayOrderResult;

/**
 * @author wxj
 * 2025/4/10
 */
public interface PayOrderResultProducer {

    void send(PayOrderResult payOrderResult);
}
