package com.anypluspay.channel.infra.persistence;

import com.anypluspay.channel.domain.institution.InstProcessOrder;
import com.anypluspay.channel.domain.repository.InstProcessOrderRepository;
import com.anypluspay.channel.infra.persistence.convertor.InstProcessOrderDalConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.InstProcessOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.InstProcessOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2024/8/6
 */
@Repository
public class InstProcessOrderRepositoryImpl implements InstProcessOrderRepository {

    @Autowired
    private InstProcessOrderDalConvertor instProcessOrderDalConvertor;

    @Autowired
    private InstProcessOrderMapper instProcessOrderMapper;

    @Override
    public void reStore(InstProcessOrder instProcessOrder) {
        instProcessOrderMapper.updateById(instProcessOrderDalConvertor.toDO(instProcessOrder));
    }

    @Override
    public void store(InstProcessOrder instProcessOrder) {
        instProcessOrderMapper.insert(instProcessOrderDalConvertor.toDO(instProcessOrder));
    }

    @Override
    public InstProcessOrder load(String instProcessOrderId) {
        return instProcessOrderDalConvertor.toEntity(instProcessOrderMapper.selectById(instProcessOrderId));
    }

    @Override
    public InstProcessOrder lock(String instProcessOrderId) {
        return instProcessOrderDalConvertor.toEntity(instProcessOrderMapper.lockById(instProcessOrderId));
    }

    @Override
    public List<InstProcessOrder> loadByInstOrderId(String instOrderId) {
        LambdaQueryWrapper<InstProcessOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InstProcessOrderDO::getInstOrderId, instOrderId);
        return instProcessOrderDalConvertor.toEntity(instProcessOrderMapper.selectList(queryWrapper));
    }
}
