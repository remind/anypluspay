package com.anypluspay.anypay.domain.trade.repository;

import com.anypluspay.anypay.domain.trade.TradeOrder;

/**
 * @author wxj
 * 2026/1/27
 */
public interface TradeOrderRepository {

    void store(TradeOrder tradeOrder);

    void reStore(TradeOrder tradeOrder);

    TradeOrder load(String tradeOrderId);

    TradeOrder loadByOutTradeNo(String outTradeNo);

    TradeOrder lock(String tradeOrderId);

    TradeOrder lockByOutTradeNo(String outTradeNo);
}
