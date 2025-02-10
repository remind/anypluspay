package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.instant.InstantPayment;

/**
 * @author wxj
 * 2024/1/17
 */
public interface InstantPaymentRepository {

    void store(InstantPayment payment);

    InstantPayment load(String paymentId);
}
