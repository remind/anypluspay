package com.anypluspay.anypay.infra.persistence.trade;

import com.anypluspay.anypay.infra.persistence.mapper.DepositOrderMapper;
import com.anypluspay.anypay.infra.persistence.trade.convertor.DepositOrderDalConvertor;
import com.anypluspay.anypay.domain.trade.DepositOrder;
import com.anypluspay.anypay.domain.trade.repository.DepositOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2025/5/14
 */
@Repository
public class DepositOrderRepositoryImpl implements DepositOrderRepository {

    @Autowired
    private DepositOrderDalConvertor dalConvertor;

    @Autowired
    private DepositOrderMapper depositOrderMapper;

    @Override
    public void store(DepositOrder depositOrder) {
        depositOrderMapper.insert(dalConvertor.toDO(depositOrder));
    }

    @Override
    public void reStore(DepositOrder depositOrder) {
        depositOrderMapper.updateById(dalConvertor.toDO(depositOrder));
    }

    @Override
    public DepositOrder load(String depositOrderId) {
        return dalConvertor.toEntity(depositOrderMapper.selectById(depositOrderId));
    }

    @Override
    public DepositOrder lock(String depositOrderId) {
        return dalConvertor.toEntity(depositOrderMapper.lockById(depositOrderId));
    }
}
