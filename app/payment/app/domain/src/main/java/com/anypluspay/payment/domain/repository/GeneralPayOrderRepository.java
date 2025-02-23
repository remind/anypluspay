package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.payorder.GeneralPayOrder;

/**
 * @author wxj
 * 2024/1/26
 */
public interface GeneralPayOrderRepository {

    void store(GeneralPayOrder payOrder);
    void reStore(GeneralPayOrder payOrder);

    GeneralPayOrder load(String payOrderId);
}
