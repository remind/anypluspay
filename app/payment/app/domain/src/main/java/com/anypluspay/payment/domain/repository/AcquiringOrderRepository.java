package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.trade.AcquiringOrder;

import java.util.List;

/**
 * @author wxj
 * 2025/5/17
 */
public interface AcquiringOrderRepository {

    void store(AcquiringOrder acquiringOrder);

    void reStore(AcquiringOrder acquiringOrder);

    AcquiringOrder load(String paymentId);
    List<AcquiringOrder> loadByRelationPaymentId(String paymentId);

    AcquiringOrder load(String outTradeNo, String partnerId);

    AcquiringOrder lock(String paymentId);

    AcquiringOrder lock(String outTradeNo, String partnerId);

}
