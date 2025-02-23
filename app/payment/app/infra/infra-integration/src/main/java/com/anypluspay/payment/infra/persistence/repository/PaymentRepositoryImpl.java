package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.repository.PaymentRepository;
import com.anypluspay.payment.infra.persistence.convertor.PaymentDalConvertor;
import com.anypluspay.payment.infra.persistence.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2025/2/22
 */
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    @Autowired
    private PaymentMapper dalMapper;

    @Autowired
    private PaymentDalConvertor dalConvertor;

    @Override
    public Payment lock(String paymentId) {
        return dalConvertor.toEntity(dalMapper.lockById(paymentId));
    }

    @Override
    public Payment load(String paymentId) {
        return dalConvertor.toEntity(dalMapper.selectById(paymentId));
    }

    @Override
    public void store(Payment payment) {
        dalMapper.insert(dalConvertor.toDO(payment));
    }

    @Override
    public void reStore(Payment payment) {
        dalMapper.updateById(dalConvertor.toDO(payment));
    }
}
