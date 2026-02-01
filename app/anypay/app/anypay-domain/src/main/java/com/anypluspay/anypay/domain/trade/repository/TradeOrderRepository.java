package com.anypluspay.anypay.domain.trade.repository;

import com.anypluspay.anypay.domain.trade.TradeOrder;

import java.util.List;

/**
 * @author wxj
 * 2026/1/27
 */
public interface TradeOrderRepository {

    void store(TradeOrder tradeOrder);

    void reStore(TradeOrder tradeOrder);

    TradeOrder load(String tradeId);

    TradeOrder loadByOutTradeNo(String outTradeNo);

    TradeOrder lock(String tradeId);

    TradeOrder lockByOutTradeNo(String outTradeNo);

    TradeOrder validateAndLoad(String tradeId, String outTradeNo);

    TradeOrder validateAndLock(String tradeId, String outTradeNo);

    List<TradeOrder> loadByRelationTradeId(String relationTradeId);
}
