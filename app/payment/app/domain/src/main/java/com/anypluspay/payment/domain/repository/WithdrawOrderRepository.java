package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.trade.withdraw.WithdrawOrder;

/**
 * @author wxj
 * 2025/5/15
 */
public interface WithdrawOrderRepository {

    void store(WithdrawOrder withdrawOrder);

    void reStore(WithdrawOrder withdrawOrder);

    WithdrawOrder load(String paymentId);

    WithdrawOrder lock(String paymentId);
}
