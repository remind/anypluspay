package com.anypluspay.channel.infra.persistence.bizorder.fundout;

import com.anypluspay.channel.domain.bizorder.fund.FundOutOrder;
import com.anypluspay.channel.infra.persistence.bizorder.BizOrderDalOperator;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.FundOutOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2024/12/19
 */
@Repository
public class FundOutDalOperator implements BizOrderDalOperator<FundOutOrder> {

    @Autowired
    private FundOutConvertor convertor;

    @Autowired
    private FundOutOrderMapper mapper;


    @Override
    public FundOutOrder load(BizOrderDO bizOrderDO) {
        return convertor.toEntity(bizOrderDO, mapper.selectById(bizOrderDO.getOrderId()));
    }

    @Override
    public void store(FundOutOrder bizOrder) {
        mapper.insert(convertor.toDO(bizOrder));
    }

    @Override
    public void reStore(FundOutOrder bizOrder) {
        mapper.updateById(convertor.toDO(bizOrder));
    }
}
