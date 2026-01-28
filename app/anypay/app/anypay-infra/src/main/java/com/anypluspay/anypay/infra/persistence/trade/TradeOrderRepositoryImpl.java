package com.anypluspay.anypay.infra.persistence.trade;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.infra.persistence.dataobject.TradeOrderDO;
import com.anypluspay.anypay.infra.persistence.mapper.TradeOrderMapper;
import com.anypluspay.anypay.infra.persistence.trade.convertor.TradeOrderDalConvertor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2026/1/27
 */
@Repository
public class TradeOrderRepositoryImpl implements TradeOrderRepository {

    @Resource
    private TradeOrderDalConvertor tradeOrderDalConvertor;

    @Resource
    private TradeOrderMapper tradeOrderMapper;

    @Override
    public void store(TradeOrder tradeOrder) {
        TradeOrderDO tradeOrderDO = tradeOrderDalConvertor.toDO(tradeOrder);
        tradeOrderMapper.insert(tradeOrderDO);
        tradeOrder.setGmtCreate(tradeOrderDO.getGmtCreate());
        tradeOrder.setGmtModified(tradeOrderDO.getGmtModified());
    }

    @Override
    public void reStore(TradeOrder tradeOrder) {
        TradeOrderDO tradeOrderDO = tradeOrderDalConvertor.toDO(tradeOrder);
        tradeOrderMapper.updateById(tradeOrderDO);
        tradeOrder.setGmtModified(tradeOrderDO.getGmtModified());
    }

    @Override
    public TradeOrder load(String tradeId) {
        return tradeOrderDalConvertor.toEntity(tradeOrderMapper.selectById(tradeId));
    }

    @Override
    public TradeOrder loadByOutTradeNo(String outTradeNo) {
        LambdaQueryWrapper<TradeOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TradeOrderDO::getOutTradeNo, outTradeNo);
        return tradeOrderDalConvertor.toEntity(tradeOrderMapper.selectOne(queryWrapper));
    }

    @Override
    public TradeOrder lock(String tradeId) {
        return tradeOrderDalConvertor.toEntity(tradeOrderMapper.lockById(tradeId));
    }

    @Override
    public TradeOrder lockByOutTradeNo(String outTradeNo) {
        LambdaQueryWrapper<TradeOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TradeOrderDO::getOutTradeNo, outTradeNo);
        return tradeOrderDalConvertor.toEntity(tradeOrderMapper.lockOne(queryWrapper));
    }
}
