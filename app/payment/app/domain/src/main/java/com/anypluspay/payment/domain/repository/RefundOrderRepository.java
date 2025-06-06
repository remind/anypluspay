package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.pay.refund.RefundOrder;

import java.util.List;

/**
 * @author wxj
 * 2025/2/18
 */
public interface RefundOrderRepository {

    void store(RefundOrder refundOrder);

    void reStore(RefundOrder refundOrder);

    RefundOrder load(String refundOrderId);

    RefundOrder lock(String refundOrderId);

    List<RefundOrder> loadByOrigPayOrderId(String origPayOrderId);
}
