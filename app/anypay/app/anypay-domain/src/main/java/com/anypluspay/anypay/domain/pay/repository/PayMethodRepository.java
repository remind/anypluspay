package com.anypluspay.anypay.domain.pay.repository;

import com.anypluspay.anypay.domain.pay.PayMethod;

/**
 * @author wxj
 * 2026/1/26
 */
public interface PayMethodRepository {

    PayMethod load(String code);
}
