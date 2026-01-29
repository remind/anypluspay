package com.anypluspay.anypay.infra.persistence.payment;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.infra.persistence.dataobject.PayOrderDO;
import com.anypluspay.anypay.infra.persistence.mapper.PayOrderMapper;
import com.anypluspay.anypay.infra.persistence.payment.convertor.PayOrderDalConvertor;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 支付订单仓储实现
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
    public PayOrder lock(String payOrderId) {
        return payOrderDalConvertor.toEntity(payOrderMapper.lockById(payOrderId));
    }
}
