package com.anypluspay.anypay.infra.persistence.trade;

import com.anypluspay.anypay.infra.persistence.dataobject.AcquiringOrderDO;
import com.anypluspay.anypay.infra.persistence.mapper.AcquiringOrderMapper;
import com.anypluspay.anypay.infra.persistence.trade.convertor.AcquiringOrderDalConvertor;
import com.anypluspay.anypay.trade.AcquiringOrder;
import com.anypluspay.anypay.trade.repository.AcquiringOrderRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2025/5/17
 */
@Repository
public class AcquiringOrderRepositoryImpl implements AcquiringOrderRepository {

    @Autowired
    private AcquiringOrderMapper dalMapper;

    @Autowired
    private AcquiringOrderDalConvertor dalConvertor;

    @Override
    public void store(AcquiringOrder acquiringOrder) {
        dalMapper.insert(dalConvertor.toDO(acquiringOrder));
    }

    @Override
    public void reStore(AcquiringOrder acquiringOrder) {
        dalMapper.updateById(dalConvertor.toDO(acquiringOrder));
    }

    @Override
    public AcquiringOrder load(String tradeId) {
        return dalConvertor.toEntity(dalMapper.selectById(tradeId));
    }

    @Override
    public List<AcquiringOrder> loadByRelationTradeId(String tradeId) {
        LambdaQueryWrapper<AcquiringOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcquiringOrderDO::getRelationTradeId, tradeId);
        return dalConvertor.toEntity(dalMapper.selectList(queryWrapper));
    }

    @Override
    public AcquiringOrder load(String outTradeNo, String partnerId) {
        LambdaQueryWrapper<AcquiringOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcquiringOrderDO::getOutTradeNo, outTradeNo);
        queryWrapper.eq(AcquiringOrderDO::getPartnerId, partnerId);
        return dalConvertor.toEntity(dalMapper.selectOne(queryWrapper));
    }

    @Override
    public AcquiringOrder lock(String tradeId) {
        return dalConvertor.toEntity(dalMapper.lockById(tradeId));
    }

    @Override
    public AcquiringOrder lock(String outTradeNo, String partnerId) {
        LambdaQueryWrapper<AcquiringOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcquiringOrderDO::getOutTradeNo, outTradeNo);
        queryWrapper.eq(AcquiringOrderDO::getPartnerId, partnerId);
        return dalConvertor.toEntity(dalMapper.lockOne(queryWrapper));
    }
}
