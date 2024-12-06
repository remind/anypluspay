package com.anypluspay.channel.infra.persistence.bizorder.fundin;

import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.infra.persistence.bizorder.BizOrderDalOperator;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.FundInOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.FundInOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2024/8/9
 */
@Repository
public class FundInDalOperator implements BizOrderDalOperator<FundInOrder> {

    @Autowired
    private FundInConvertor fundInConvertor;

    @Autowired
    private FundInOrderMapper fundInOrderMapper;

    public void store(FundInOrder bizOrder) {
        FundInOrderDO fundInOrderDO = fundInConvertor.toDO(bizOrder);
        fundInOrderMapper.insert(fundInOrderDO);
    }

    public void reStore(FundInOrder bizOrder) {
        FundInOrderDO fundInOrderDO = fundInConvertor.toDO(bizOrder);
        fundInOrderMapper.updateById(fundInOrderDO);
    }

    public FundInOrder load(BizOrderDO bizOrderDO) {
        return fundInConvertor.toEntity(bizOrderDO, fundInOrderMapper.selectById(bizOrderDO.getOrderId()));
    }
}
