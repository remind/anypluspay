package com.anypluspay.payment.infra.persistence.repository.process;

import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
import com.anypluspay.payment.infra.persistence.convertor.PayOrderConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.PayOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.PayOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wxj
 * 2024/1/27
 */
@Repository
public class PayOrderRepositoryImpl extends AbstractPayOrderRepository implements PayOrderRepository {

    @Autowired
    private PayOrderMapper dalMapper;

    @Autowired
    private PayOrderConvertor dalConvertor;

    @Override
    public void store(PayOrder payOrder) {
        dalMapper.insert(dalConvertor.toDO(payOrder));
        storeFundDetail(payOrder.getPayerDetails());
        storeFundDetail(payOrder.getPayeeDetails());
    }

    @Override
    public void reStore(PayOrder payOrder) {
        dalMapper.updateById(dalConvertor.toDO(payOrder));
    }

    @Override
    public PayOrder load(String payOrderId) {
        PayOrder payOrder = dalConvertor.toEntity(dalMapper.selectById(payOrderId));
        fillFundDetails(payOrder);
        return payOrder;
    }

    @Override
    public PayOrder lock(String payOrderId) {
        PayOrder payOrder = dalConvertor.toEntity(dalMapper.lockById(payOrderId));
        fillFundDetails(payOrder);
        return payOrder;
    }

    @Override
    public List<PayOrder> loadByTradeId(String tradeId) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrderDO::getTradeId, tradeId);
        List<PayOrder> payOrders = dalConvertor.toEntity(dalMapper.selectList(queryWrapper));
        if (!CollectionUtils.isEmpty(payOrders)) {
            payOrders.forEach(this::fillFundDetails);
        }
        return payOrders;
    }

}
