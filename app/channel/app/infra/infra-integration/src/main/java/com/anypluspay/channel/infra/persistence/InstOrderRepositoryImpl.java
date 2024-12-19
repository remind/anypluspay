package com.anypluspay.channel.infra.persistence;

import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstOrderType;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.infra.persistence.convertor.InstOrderDalConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.InstOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2024/8/6
 */
@Repository
public class InstOrderRepositoryImpl implements InstOrderRepository {

    @Autowired
    private InstOrderDalConvertor instOrderDalConvertor;

    @Autowired
    private InstOrderMapper instOrderMapper;

    @Override
    public void store(InstOrder instOrder) {
        InstOrderDO instOrderDO = instOrderDalConvertor.toDO(instOrder, InstOrderType.FUND_IN.getCode());
        instOrderMapper.insert(instOrderDO);
        instOrder.setInstOrderId(instOrderDO.getInstOrderId());
    }

    @Override
    public void reStore(InstOrder instOrder) {
        InstOrderDO instOrderDO = instOrderDalConvertor.toDO(instOrder, InstOrderType.FUND_IN.getCode());
        instOrderMapper.updateById(instOrderDO);
    }

    @Override
    public InstOrder load(Long instOrderId) {
        InstOrderDO instOrderDO = instOrderMapper.selectById(instOrderId);
        return instOrderDalConvertor.toEntity(instOrderDO);
    }

    @Override
    public InstOrder lock(Long instOrderId) {
        InstOrderDO instOrderDO = instOrderMapper.lockById(instOrderId);
        return instOrderDalConvertor.toEntity(instOrderDO);
    }

    @Override
    public InstOrder loadByInstRequestNo(String instRequestNo) {
        LambdaQueryWrapper<InstOrderDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InstOrderDO::getInstRequestNo, instRequestNo);
        InstOrderDO instOrderDO = instOrderMapper.selectOne(wrapper);
        return instOrderDalConvertor.toEntity(instOrderDO);
    }
}
