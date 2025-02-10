package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.payorder.BasePayOrder;

/**
 * @author wxj
 * 2024/1/26
 */
public interface BasePayOrderRepository {

    @SuppressWarnings("rawtypes")
    void reStore(BasePayOrder payOrder);
}
