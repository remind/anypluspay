package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.repository.WithdrawOrderRepository;
import com.anypluspay.payment.domain.trade.withdraw.WithdrawOrder;
import com.anypluspay.payment.infra.persistence.convertor.WithdrawOrderDalConvertor;
import com.anypluspay.payment.infra.persistence.mapper.WithdrawOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2025/5/15
 */
@Repository
public class WithdrawOrderRepositoryImpl implements WithdrawOrderRepository {

    @Autowired
    private WithdrawOrderDalConvertor dalConvertor;

    @Autowired
    private WithdrawOrderMapper dalMapper;

    @Override
    public void store(WithdrawOrder withdrawOrder) {
        dalMapper.insert(dalConvertor.toDO(withdrawOrder));
    }

    @Override
    public void reStore(WithdrawOrder withdrawOrder) {
        dalMapper.updateById(dalConvertor.toDO(withdrawOrder));
    }

    @Override
    public WithdrawOrder load(String paymentId) {
        return dalConvertor.toEntity(dalMapper.selectById(paymentId));
    }

    @Override
    public WithdrawOrder lock(String paymentId) {
        return dalConvertor.toEntity(dalMapper.lockById(paymentId));
    }
}
