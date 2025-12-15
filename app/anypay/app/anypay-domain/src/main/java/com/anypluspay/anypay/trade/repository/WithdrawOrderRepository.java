package com.anypluspay.anypay.trade.repository;


import com.anypluspay.anypay.trade.WithdrawOrder;

/**
 * @author wxj
 * 2025/5/15
 */
public interface WithdrawOrderRepository {

    void store(WithdrawOrder withdrawOrder);

    void reStore(WithdrawOrder withdrawOrder);

    WithdrawOrder load(String tradeId);

    WithdrawOrder lock(String tradeId);
}
