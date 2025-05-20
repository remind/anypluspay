package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.domain.flux.FluxProcessType;
import com.anypluspay.payment.domain.repository.FluxProcessRepository;
import com.anypluspay.payment.infra.persistence.convertor.FluxProcessDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.FluxProcessDO;
import com.anypluspay.payment.infra.persistence.mapper.FluxProcessMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2024/1/28
 */
@Repository
public class FluxProcessRepositoryImpl implements FluxProcessRepository {

    @Autowired
    private FluxProcessMapper dalMapper;

    @Autowired
    private FluxProcessDalConvertor dalConvertor;

    @Override
    public List<FluxProcess> loadByFluxOrderId(String fluxOrderId) {
        LambdaQueryWrapper<FluxProcessDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FluxProcessDO::getFluxOrderId, fluxOrderId);
        return dalConvertor.toEntity(dalMapper.selectList(queryWrapper));
    }

    @Override
    public FluxProcess loadByPayFundDetailId(String fundDetailId) {
        LambdaQueryWrapper<FluxProcessDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FluxProcessDO::getFundDetailId, fundDetailId);
        queryWrapper.eq(FluxProcessDO::getType, FluxProcessType.NORMAL.getCode());
        return dalConvertor.toEntity(dalMapper.selectOne(queryWrapper));
    }

    @Override
    public FluxProcess load(String fluxProcessId) {
        return dalConvertor.toEntity(dalMapper.selectById(fluxProcessId));
    }

    @Override
    public void store(FluxProcess fluxProcess) {
        dalMapper.insert(dalConvertor.toDO(fluxProcess));
    }

    @Override
    public void reStore(FluxProcess fluxProcess) {
        dalMapper.updateById(dalConvertor.toDO(fluxProcess));
    }

    @Override
    public void remove(List<String> fluxProcessIds) {
        fluxProcessIds.forEach(instructionId -> dalMapper.deleteById(instructionId));
    }
}
