package com.anypluspay.anypay.domain.pay.repository;

import com.anypluspay.anypay.domain.pay.PayOrder;

import java.util.List;

/**
 * @author wxj
 * 2025/12/20
 */
public interface PayOrderRepository {

    void store(PayOrder payOrder);

    void reStore(PayOrder payOrder);

    PayOrder load(String payOrderId);

    List<PayOrder> loadByTradeId(String tradeId);

    List<PayOrder> loadByOrigPayId(String origPayId);

    PayOrder loadByChannelRequestNo(String channelCode, String channelRequestNo);

    PayOrder loadByChannelResponseNo(String channelCode, String channelResponseNo);

    PayOrder lock(String payOrderId);
}
