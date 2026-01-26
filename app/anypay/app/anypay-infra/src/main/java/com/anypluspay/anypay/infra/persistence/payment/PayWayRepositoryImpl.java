package com.anypluspay.anypay.infra.persistence.payment;

import com.anypluspay.anypay.domain.pay.PayWay;
import com.anypluspay.anypay.domain.pay.repository.PayWayRepository;
import com.anypluspay.anypay.infra.persistence.mapper.PayWayMapper;
import com.anypluspay.anypay.infra.persistence.payment.convertor.PayWayDalConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2026/1/26
 */
@Repository
public class PayWayRepositoryImpl implements PayWayRepository {

    @Autowired
    private PayWayDalConvertor payWayDalConvertor;

    @Autowired
    private PayWayMapper payWayMapper;

    @Override
    public PayWay load(String code) {
        return payWayDalConvertor.toEntity(payWayMapper.selectById(code));
    }
}
