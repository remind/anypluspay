package com.anypluspay.anypay.infra.persistence.trade;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.infra.persistence.dataobject.TradeOrderDO;
import com.anypluspay.anypay.infra.persistence.mapper.TradeOrderMapper;
import com.anypluspay.anypay.infra.persistence.trade.convertor.TradeOrderDalConvertor;
import com.anypluspay.commons.exceptions.BizException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

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

    @Override
    public TradeOrder validateAndLoad(String tradeId, String outTradeNo) {
        TradeOrder tradeOrder;
        if (StringUtils.isNotBlank(tradeId)) {
            tradeOrder = load(tradeId);
        } else if (StringUtils.isNotBlank(outTradeNo)) {
            tradeOrder = loadByOutTradeNo(outTradeNo);
        } else {
            throw new BizException("参数错误");
        }
        Assert.isTrue(tradeOrder != null, "订单不存在");
        return tradeOrder;
    }

    @Override
    public TradeOrder validateAndLock(String tradeId, String outTradeNo) {
        TradeOrder tradeOrder;
        if (StringUtils.isNotBlank(tradeId)) {
            tradeOrder = lock(tradeId);
        } else if (StringUtils.isNotBlank(outTradeNo)) {
            tradeOrder = lockByOutTradeNo(outTradeNo);
        } else {
            throw new BizException("参数错误");
        }
        Assert.isTrue(tradeOrder != null, "订单不存在");
        return tradeOrder;
    }

    @Override
    public List<TradeOrder> loadByRelationTradeId(String relationTradeId) {
        LambdaQueryWrapper<TradeOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TradeOrderDO::getRelationTradeId, relationTradeId);
        return tradeOrderDalConvertor.toEntity(tradeOrderMapper.selectList(queryWrapper));
    }
}
