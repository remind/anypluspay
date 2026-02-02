package com.anypluspay.anypay.infra.persistence.payment;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.infra.persistence.dataobject.PayOrderDO;
import com.anypluspay.anypay.infra.persistence.mapper.PayOrderMapper;
import com.anypluspay.anypay.infra.persistence.payment.convertor.PayOrderDalConvertor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 支付订单仓储实现
 *
 * @author wxj
 * 2026/1/26
 */
@Repository
public class PayOrderRepositoryImpl implements PayOrderRepository {

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private PayOrderDalConvertor payOrderDalConvertor;

    @Override
    public void store(PayOrder payOrder) {
        PayOrderDO payOrderDO = payOrderDalConvertor.toDO(payOrder);
        payOrderMapper.insert(payOrderDO);
        payOrder.setGmtCreate(payOrderDO.getGmtCreate());
        payOrder.setGmtModified(payOrderDO.getGmtModified());
    }

    @Override
    public void reStore(PayOrder payOrder) {
        PayOrderDO payOrderDO = payOrderDalConvertor.toDO(payOrder);
        payOrderMapper.updateById(payOrderDO);
        payOrder.setGmtModified(payOrderDO.getGmtModified());
    }

    @Override
    public PayOrder load(String payOrderId) {
        return payOrderDalConvertor.toEntity(payOrderMapper.selectById(payOrderId));
    }

    @Override
    public List<PayOrder> loadByTradeId(String tradeId) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrderDO::getTradeId, tradeId);
        return payOrderDalConvertor.toEntity(payOrderMapper.selectList(queryWrapper));
    }

    @Override
    public List<PayOrder> loadByOrigPayId(String origPayId) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrderDO::getOrigPayId, origPayId);
        return payOrderDalConvertor.toEntity(payOrderMapper.selectList(queryWrapper));
    }

    @Override
    public PayOrder loadByChannelRequestNo(String channelCode, String channelRequestNo) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrderDO::getChannelCode, channelCode)
                .eq(PayOrderDO::getChannelRequestNo, channelRequestNo);
        return payOrderDalConvertor.toEntity(payOrderMapper.selectOne(queryWrapper));
    }

    @Override
    public PayOrder loadByChannelResponseNo(String channelCode, String channelResponseNo) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrderDO::getChannelCode, channelCode)
                .eq(PayOrderDO::getChannelResponseNo, channelResponseNo);
        return payOrderDalConvertor.toEntity(payOrderMapper.selectOne(queryWrapper));
    }

    @Override
    public PayOrder lock(String payOrderId) {
        return payOrderDalConvertor.toEntity(payOrderMapper.lockById(payOrderId));
    }
}
