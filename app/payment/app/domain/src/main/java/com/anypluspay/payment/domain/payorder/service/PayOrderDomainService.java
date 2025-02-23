package com.anypluspay.payment.domain.payorder.service;

import com.anypluspay.payment.domain.payorder.GeneralPayOrder;
import com.anypluspay.payment.types.PayResult;

/**
 * @author wxj
 * 2024/1/27
 */
public interface PayOrderDomainService {

    PayResult pay(GeneralPayOrder generalPayOrder);

}
