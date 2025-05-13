package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.InstructionType;
import com.anypluspay.payment.domain.repository.FluxInstructionRepository;
import com.anypluspay.payment.infra.persistence.dataobject.FluxInstructionDO;
import com.anypluspay.payment.infra.persistence.mapper.FluxInstructionMapper;
import com.anypluspay.payment.infra.persistence.convertor.FluxInstructionDalConvertor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2024/1/28
 */
@Repository
public class FluxInstructionRepositoryImpl implements FluxInstructionRepository {

    @Autowired
    private FluxInstructionMapper dalMapper;

    @Autowired
    private FluxInstructionDalConvertor dalConvertor;

    @Override
    public List<FluxInstruction> loadByFluxOrderId(String fluxOrderId) {
        LambdaQueryWrapper<FluxInstructionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FluxInstructionDO::getFluxOrderId, fluxOrderId);
        return dalConvertor.toEntity(dalMapper.selectList(queryWrapper));
    }

    @Override
    public FluxInstruction loadByPayFundDetailId(String fundDetailId) {
        LambdaQueryWrapper<FluxInstructionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FluxInstructionDO::getFundDetailId, fundDetailId);
        queryWrapper.eq(FluxInstructionDO::getType, InstructionType.NORMAL.getCode());
        return dalConvertor.toEntity(dalMapper.selectOne(queryWrapper));
    }

    @Override
    public FluxInstruction load(String fluxInstructionId) {
        return dalConvertor.toEntity(dalMapper.selectById(fluxInstructionId));
    }

    @Override
    public void store(FluxInstruction fluxInstruction) {
        FluxInstructionDO instructionDO = dalConvertor.toDO(fluxInstruction);
        dalMapper.insert(instructionDO);
    }

    @Override
    public void reStore(FluxInstruction fluxInstruction) {
        FluxInstructionDO instructionDO = dalConvertor.toDO(fluxInstruction);
        dalMapper.updateById(instructionDO);
    }

    @Override
    public void remove(List<String> fluxInstructionIds) {
        fluxInstructionIds.forEach(instructionId -> dalMapper.deleteById(instructionId));
    }
}
