package com.anypluspay.anypay.infra.persistence.payment;

import com.anypluspay.anypay.domain.pay.PayMethod;
import com.anypluspay.anypay.domain.pay.repository.PayMethodRepository;
import com.anypluspay.anypay.infra.persistence.mapper.PayMethodMapper;
import com.anypluspay.anypay.infra.persistence.payment.convertor.PayMethodDalConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2026/1/26
 */
@Repository
public class PayMethodRepositoryImpl implements PayMethodRepository {

    @Autowired
    private PayMethodDalConvertor payMethodDalConvertor;

    @Autowired
    private PayMethodMapper payMethodMapper;

    @Override
    public PayMethod load(String code) {
        return payMethodDalConvertor.toEntity(payMethodMapper.selectById(code));
    }
}
