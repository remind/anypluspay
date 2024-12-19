package com.anypluspay.channel.infra.persistence.bizorder.refund;

import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.infra.persistence.bizorder.BizOrderDalOperator;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.RefundOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2024/8/20
 */
@Repository
public class RefundDalOperator implements BizOrderDalOperator<RefundOrder> {

    @Autowired
    private RefundConvertor refundConvertor;

    @Autowired
    private RefundOrderMapper refundOrderMapper;

    public void store(RefundOrder bizOrder) {
        refundOrderMapper.insert(refundConvertor.toDO(bizOrder));
    }

    public void reStore(RefundOrder bizOrder) {
        refundOrderMapper.updateById(refundConvertor.toDO(bizOrder));
    }

    public RefundOrder load(BizOrderDO bizOrderDO) {
        return refundConvertor.toEntity(bizOrderDO, refundOrderMapper.selectById(bizOrderDO.getOrderId()));
    }
}
