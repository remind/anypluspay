package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.process.PayProcess;

import java.util.List;

/**
 * @author wxj
 * 2024/1/26
 */
public interface PayProcessRepository {

    void store(PayProcess payOrder);
    void reStore(PayProcess payOrder);

    PayProcess load(String payProcessId);

    PayProcess lock(String payProcessId);

    PayProcess loadByRequestId(String requestId);

    List<PayProcess> loadByPaymentId(String paymentId);
}
