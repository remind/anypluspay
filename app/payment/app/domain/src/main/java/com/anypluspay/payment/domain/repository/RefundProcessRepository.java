package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.process.refund.RefundProcess;

import java.util.List;

/**
 * @author wxj
 * 2025/2/18
 */
public interface RefundProcessRepository {

    void store(RefundProcess refundProcess);

    void reStore(RefundProcess refundProcess);

    RefundProcess load(String refundProcessId);

    RefundProcess lock(String refundProcessId);

    List<RefundProcess> loadByPayProcessId(String payProcessId);
}
