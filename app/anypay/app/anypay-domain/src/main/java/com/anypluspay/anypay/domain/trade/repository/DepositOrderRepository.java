package com.anypluspay.anypay.domain.trade.repository;


import com.anypluspay.anypay.domain.trade.DepositOrder;

/**
 * @author wxj
 * 2025/5/14
 */
public interface DepositOrderRepository {

    void store(DepositOrder depositOrder);

    void reStore(DepositOrder depositOrder);

    DepositOrder load(String depositOrderId);

    DepositOrder lock(String depositOrderId);
}
