package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.pay.pay.PayOrder;

import java.util.List;

/**
 * @author wxj
 * 2024/1/26
 */
public interface PayOrderRepository {

    void store(PayOrder payOrder);
    void reStore(PayOrder payOrder);

    PayOrder load(String payOrderId);

    PayOrder lock(String payOrderId);

    List<PayOrder> loadByTradeId(String tradeId);
}
