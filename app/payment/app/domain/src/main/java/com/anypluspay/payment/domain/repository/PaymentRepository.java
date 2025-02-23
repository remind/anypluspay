package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.Payment;

/**
 * @author wxj
 * 2025/2/22
 */
public interface PaymentRepository {

    Payment lock(String paymentId);

    Payment load(String paymentId);

    void store(Payment payment);

    void reStore(Payment payment);
}
