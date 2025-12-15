package com.anypluspay.anypay.infra.persistence.trade;

import com.anypluspay.anypay.infra.persistence.mapper.WithdrawOrderMapper;
import com.anypluspay.anypay.infra.persistence.trade.convertor.WithdrawOrderDalConvertor;
import com.anypluspay.anypay.trade.WithdrawOrder;
import com.anypluspay.anypay.trade.repository.WithdrawOrderRepository;
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
    public WithdrawOrder load(String tradeId) {
        return dalConvertor.toEntity(dalMapper.selectById(tradeId));
    }

    @Override
    public WithdrawOrder lock(String tradeId) {
        return dalConvertor.toEntity(dalMapper.lockById(tradeId));
    }
}
