package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.chain.InstructChain;
import com.anypluspay.payment.domain.repository.FluxInstructionRepository;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.infra.persistence.convertor.FluxOrderDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.FluxOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.FluxOrderMapper;
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
    private FluxOrderDalConvertor dalConvertor;

    @Autowired
    private FluxInstructionRepository instructionRepository;

    @Override
    public void store(FluxOrder fluxOrder) {
        FluxOrderDO fluxOrderDO = dalConvertor.toDO(fluxOrder);
        dalMapper.insert(fluxOrderDO);
        List<FluxInstruction> fluxInstructions = fluxOrder.getInstructChain().toList();
        if (!CollectionUtils.isEmpty(fluxInstructions)) {
            for (FluxInstruction fluxInstruction : fluxInstructions) {
                instructionRepository.store(fluxInstruction);
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
    public FluxOrder loadByPayOrderId(String payOrderId) {
        LambdaQueryWrapper<FluxOrderDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FluxOrderDO::getPayOrderId, payOrderId);
        return buildFluxOrder(dalMapper.selectOne(wrapper));
    }

    private FluxOrder buildFluxOrder(FluxOrderDO fluxOrderDO) {
        FluxOrder fluxOrder = dalConvertor.toEntity(fluxOrderDO);
        if (fluxOrder != null) {
            List<FluxInstruction> fluxInstructions = instructionRepository.loadByFluxOrderId(fluxOrder.getFluxOrderId());
            InstructChain instructChain = new InstructChain();
            if (!CollectionUtils.isEmpty(fluxInstructions)) {
                FluxInstruction fluxInstruction;
                do {
                    final String nextInstructionId = instructChain.getTail() == null ? PaymentConstants.DEFAULT_NULL_VALUE : instructChain.getTail().getFluxInstruction().getInstructionId();
                    fluxInstruction = fluxInstructions.stream().filter(fi -> nextInstructionId.equals(fi.getPreInstructionId())).findFirst().orElse(null);
                    if (fluxInstruction != null) {
                        instructChain.append(fluxInstruction);
                    }
                } while (fluxInstruction != null);
            }
            fluxOrder.setInstructChain(instructChain);
        }
        return fluxOrder;
    }
}
