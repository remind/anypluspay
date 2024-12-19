package com.anypluspay.channel.infra.persistence;

import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.repository.InstCommandOrderRepository;
import com.anypluspay.channel.infra.persistence.convertor.InstCommandOrderDalConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.InstCommandOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.InstCommandOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2024/8/6
 */
@Repository
public class InstCommandOrderRepositoryImpl implements InstCommandOrderRepository {

    @Autowired
    private InstCommandOrderDalConvertor convertor;

    @Autowired
    private InstCommandOrderMapper mapper;

    @Override
    public void reStore(InstCommandOrder instCommandOrder) {
        mapper.updateById(convertor.toDO(instCommandOrder));
    }

    @Override
    public void store(InstCommandOrder instCommandOrder) {
        InstCommandOrderDO instCommandOrderDO = convertor.toDO(instCommandOrder);
        mapper.insert(instCommandOrderDO);
        instCommandOrder.setCommandId(instCommandOrderDO.getCommandId());
    }

    @Override
    public InstCommandOrder load(Long commandId) {
        return convertor.toEntity(mapper.selectById(commandId));
    }

    @Override
    public InstCommandOrder lock(Long commandId) {
        return convertor.toEntity(mapper.lockById(commandId));
    }

    @Override
    public List<InstCommandOrder> loadByInstOrderId(Long instOrderId) {
        LambdaQueryWrapper<InstCommandOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InstCommandOrderDO::getInstOrderId, instOrderId);
        return convertor.toEntity(mapper.selectList(queryWrapper));
    }
}
