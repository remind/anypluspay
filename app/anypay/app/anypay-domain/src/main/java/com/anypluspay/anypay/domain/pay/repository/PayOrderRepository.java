package com.anypluspay.anypay.domain.pay.repository;

import com.anypluspay.anypay.domain.member.Member;
import com.anypluspay.anypay.domain.pay.PayOrder;

/**
 * @author wxj
 * 2025/12/20
 */
public interface PayOrderRepository {

    void store(PayOrder payOrder);

    void reStore(PayOrder payOrder);

    Member load(String payOrderId);

    Member lock(String payOrderId);
}
