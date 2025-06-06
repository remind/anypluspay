package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.domain.flux.chain.FluxChain;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.repository.FluxProcessRepository;
import com.anypluspay.payment.infra.persistence.convertor.FluxOrderDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.FluxOrderDO;
import com.anypluspay.payment.infra.persistence.dataobject.FluxProcessDO;
import com.anypluspay.payment.infra.persistence.mapper.FluxOrderMapper;
import com.anypluspay.payment.infra.persistence.mapper.FluxProcessMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wxj
 * 2024/1/28
 */
@Repository
public class FluxOrderRepositoryImpl implements FluxOrderRepository {

    @Autowired
    private FluxOrderMapper dalMapper;

    @Autowired
    private FluxProcessMapper instructionMapper;

    @Autowired
    private FluxOrderDalConvertor dalConvertor;

    @Autowired
    private FluxProcessRepository instructionRepository;

    @Override
    public void store(FluxOrder fluxOrder) {
        FluxOrderDO fluxOrderDO = dalConvertor.toDO(fluxOrder);
        dalMapper.insert(fluxOrderDO);
        List<FluxProcess> fluxProcesses = fluxOrder.getFluxChain().toList();
        if (!CollectionUtils.isEmpty(fluxProcesses)) {
            for (FluxProcess fluxProcess : fluxProcesses) {
                instructionRepository.store(fluxProcess);
            }
        }
    }

    @Override
    public void reStore(FluxOrder fluxOrder) {
        dalMapper.updateById(dalConvertor.toDO(fluxOrder));
    }

    @Override
    public FluxOrder load(String fluxOrderId) {
        return buildFluxOrder(dalMapper.selectById(fluxOrderId));
    }

    @Override
    public FluxOrder lock(String fluxOrderId) {
        return buildFluxOrder(dalMapper.lockById(fluxOrderId));
    }

    @Override
    public FluxOrder loadByPayOrderId(String orderId) {
        LambdaQueryWrapper<FluxOrderDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FluxOrderDO::getOrderId, orderId);
        return buildFluxOrder(dalMapper.selectOne(wrapper));
    }

    @Override
    public FluxOrder loadByInstructionId(String fluxProcessId) {
        FluxProcessDO fluxProcessDO = instructionMapper.selectById(fluxProcessId);
        return fluxProcessDO != null ? load(fluxProcessDO.getFluxOrderId()) : null;
    }

    private FluxOrder buildFluxOrder(FluxOrderDO fluxOrderDO) {
        FluxOrder fluxOrder = dalConvertor.toEntity(fluxOrderDO);
        if (fluxOrder != null) {
            List<FluxProcess> fluxProcesses = instructionRepository.loadByFluxOrderId(fluxOrder.getFluxOrderId());
            FluxChain fluxChain = new FluxChain();
            if (!CollectionUtils.isEmpty(fluxProcesses)) {
                FluxProcess fluxProcess;
                do {
                    final String nextFluxProcessId = fluxChain.getTail() == null ? PaymentConstants.DEFAULT_NULL_VALUE : fluxChain.getTail().getFluxProcess().getFluxProcessId();
                    fluxProcess = fluxProcesses.stream().filter(fi -> nextFluxProcessId.equals(fi.getPreProcessId())).findFirst().orElse(null);
                    if (fluxProcess != null) {
                        fluxChain.append(fluxProcess);
                    }
                } while (fluxProcess != null);
            }
            fluxOrder.setFluxChain(fluxChain);
        }
        return fluxOrder;
    }
}
