package com.anypluspay.anypay.infra.persistence.trade;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2026/1/27
 */
@Repository
public class TradeOrderRepositoryImpl implements TradeOrderRepository {
    @Override
    public void store(TradeOrder tradeOrder) {

    }

    @Override
    public void reStore(TradeOrder tradeOrder) {

    }

    @Override
    public TradeOrder load(String tradeOrderId) {
        return null;
    }

    @Override
    public TradeOrder loadByOutTradeNo(String outTradeNo) {
        return null;
    }

    @Override
    public TradeOrder lock(String tradeOrderId) {
        return null;
    }

    @Override
    public TradeOrder lockByOutTradeNo(String outTradeNo) {
        return null;
    }
}
