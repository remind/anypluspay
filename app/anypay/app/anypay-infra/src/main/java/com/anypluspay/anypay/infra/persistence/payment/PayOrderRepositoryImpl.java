package com.anypluspay.anypay.infra.persistence.payment;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2026/1/26
 */
@Repository
public class PayOrderRepositoryImpl implements PayOrderRepository {
    @Override
    public void store(PayOrder payOrder) {

    }

    @Override
    public void reStore(PayOrder payOrder) {

    }

    @Override
    public PayOrder load(String payOrderId) {
        return null;
    }

    @Override
    public PayOrder lock(String payOrderId) {
        return null;
    }
}
