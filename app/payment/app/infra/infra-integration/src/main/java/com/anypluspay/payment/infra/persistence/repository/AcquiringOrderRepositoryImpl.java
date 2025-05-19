package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.domain.biz.acquiring.AcquiringOrder;
import com.anypluspay.payment.infra.persistence.convertor.AcquiringOrderDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.AcquiringOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.AcquiringOrderMapper;
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
    public AcquiringOrder load(String paymentId) {
        return dalConvertor.toEntity(dalMapper.selectById(paymentId));
    }

    @Override
    public List<AcquiringOrder> loadByRelationPaymentId(String paymentId) {
        LambdaQueryWrapper<AcquiringOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcquiringOrderDO::getRelationPaymentId, paymentId);
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
    public AcquiringOrder lock(String paymentId) {
        return dalConvertor.toEntity(dalMapper.lockById(paymentId));
    }

    @Override
    public AcquiringOrder lock(String outTradeNo, String partnerId) {
        LambdaQueryWrapper<AcquiringOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AcquiringOrderDO::getOutTradeNo, outTradeNo);
        queryWrapper.eq(AcquiringOrderDO::getPartnerId, partnerId);
        return dalConvertor.toEntity(dalMapper.lockOne(queryWrapper));
    }
}
