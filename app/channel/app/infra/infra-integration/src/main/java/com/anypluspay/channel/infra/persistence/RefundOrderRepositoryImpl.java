package com.anypluspay.channel.infra.persistence;

import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.domain.repository.RefundOrderRepository;
import com.anypluspay.channel.infra.persistence.bizorder.refund.RefundConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.RefundOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.BizOrderMapper;
import com.anypluspay.channel.infra.persistence.mapper.RefundOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2024/8/21
 */
@Repository
public class RefundOrderRepositoryImpl implements RefundOrderRepository {

    @Autowired
    private RefundOrderMapper refundOrderMapper;

    @Autowired
    private RefundConvertor refundConvertor;

    @Autowired
    private BizOrderMapper bizOrderMapper;

    @Override
    public List<RefundOrder> loadByOrigOrderId(String orderId) {
        final List<RefundOrder> refundOrders = new ArrayList<>();
        LambdaQueryWrapper<RefundOrderDO> refundOrderQuery = new LambdaQueryWrapper<>();
        refundOrderQuery.eq(RefundOrderDO::getOrigOrderId, orderId);
        List<RefundOrderDO> refundOrderDOS = refundOrderMapper.selectList(refundOrderQuery);
        if (!CollectionUtils.isEmpty(refundOrderDOS)) {
            List<String> refundOrderIds = refundOrderDOS.stream().map(RefundOrderDO::getOrderId).toList();
            LambdaQueryWrapper<BizOrderDO> bizOrderQuery = new LambdaQueryWrapper<>();
            bizOrderQuery.in(BizOrderDO::getOrderId, refundOrderIds);
            List<BizOrderDO> bizOrderDOS = bizOrderMapper.selectList(bizOrderQuery);
            bizOrderDOS.forEach(bizOrderDO -> {
                RefundOrderDO refundOrderDO = refundOrderDOS.stream().filter(ro -> ro.getOrderId().equals(bizOrderDO.getOrderId())).findFirst().get();
                refundOrders.add(refundConvertor.toEntity(bizOrderDO, refundOrderDO));
            });
        }
        return refundOrders;
    }
}
