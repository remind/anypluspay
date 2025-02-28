package com.anypluspay.payment.infra.persistence.repository.payorder;

import com.anypluspay.payment.domain.payorder.refund.RefundOrder;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.infra.persistence.convertor.RefundOrderDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.RefundOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.RefundOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 退款仓储
 *
 * @author wxj
 * 2025/2/22
 */
@Repository
public class RefundOrderRepositoryImpl extends AbstractPayOrderRepository implements RefundOrderRepository {

    @Autowired
    private RefundOrderMapper dalMapper;

    @Autowired
    private RefundOrderDalConvertor dalConvertor;

    @Override
    public void store(RefundOrder refundOrder) {
        dalMapper.insert(dalConvertor.toDO(refundOrder));
        storeFundDetail(refundOrder.getPayerDetails());
        storeFundDetail(refundOrder.getPayeeDetails());
    }

    @Override
    public void reStore(RefundOrder refundOrder) {
        dalMapper.updateById(dalConvertor.toDO(refundOrder));
    }

    @Override
    public RefundOrder load(String refundOrderId) {
        RefundOrderDO refundOrderDO = dalMapper.selectById(refundOrderId);
        RefundOrder refundOrder = dalConvertor.toEntity(refundOrderDO);
        fillFundDetails(refundOrder);
        return refundOrder;
    }

    @Override
    public List<RefundOrder> loadByPayOrderId(String payOrderId) {
        LambdaQueryWrapper<RefundOrderDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RefundOrderDO::getRelationId, payOrderId);
        List<RefundOrder> refundOrders = dalConvertor.toEntity(dalMapper.selectList(wrapper));
        if (!CollectionUtils.isEmpty(refundOrders)) {
            refundOrders.forEach(this::fillFundDetails);
        }
        return refundOrders;
    }

}
