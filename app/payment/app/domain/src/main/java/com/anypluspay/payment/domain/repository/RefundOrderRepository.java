package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.payorder.refund.RefundOrder;

import java.util.List;

/**
 * @author wxj
 * 2025/2/18
 */
public interface RefundOrderRepository {

    void store(RefundOrder refundOrder);

    void reStore(RefundOrder refundOrder);

    RefundOrder load(String refundOrderId);
    List<RefundOrder> loadByPayOrderId(String payOrderId);
}
