package com.anypluspay.payment.domain.flux.service.impl;

import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.InstructStatus;
import com.anypluspay.payment.domain.flux.InstructionType;
import com.anypluspay.payment.domain.flux.chain.InstructChain;
import com.anypluspay.payment.domain.flux.service.AbstractFluxService;
import com.anypluspay.payment.domain.flux.service.FluxOrderDomainService;
import com.anypluspay.payment.domain.repository.FluxInstructionRepository;
import com.anypluspay.payment.types.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wxj
 * 2024/1/26
 */
@Service
public class FluxOrderDomainServiceImpl extends AbstractFluxService implements FluxOrderDomainService {

    @Autowired
    private FluxInstructionRepository fluxInstructionRepository;

    @Override
    public void deleteAfterFluxInstruct(FluxOrder fluxOrder, String instructId) {
        List<String> deleteIds = fluxOrder.getInstructChain().deleteAfterNode(instructId);
        fluxInstructionRepository.remove(deleteIds);
    }

    @Override
    public FluxInstruction createReverseInstruct(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        FluxInstruction reverseInstruct = new FluxInstruction();
        InstructChain instructChain = fluxOrder.getInstructChain();
        reverseInstruct.setInstructionId(idGeneratorService.genIdByRelateId(fluxInstruction.getFluxOrderId(), IdType.FLUX_INSTRUCT_ID));
        reverseInstruct.setFluxOrderId(fluxInstruction.getFluxOrderId());
        reverseInstruct.setPayOrderId(fluxInstruction.getPayOrderId());
        reverseInstruct.setPaymentId(fluxInstruction.getPaymentId());
        reverseInstruct.setType(InstructionType.REFUND);
        reverseInstruct.setAmount(fluxInstruction.getAmount());
        reverseInstruct.setStatus(InstructStatus.INIT);
        reverseInstruct.setFundDetailId(fluxInstruction.getFundDetailId());
        reverseInstruct.setRelationId(fluxInstruction.getInstructionId());
        reverseInstruct.setFundAction(fluxInstruction.getFundAction().reverse());
        reverseInstruct.setAssetInfo(fluxInstruction.getAssetInfo());
        instructChain.append(reverseInstruct);
        fluxInstructionRepository.store(reverseInstruct);
        return reverseInstruct;
    }

    @Override
    public void insertInstruct(FluxOrder fluxOrder, FluxInstruction fluxInstruction, List<FluxInstruction> newFluxInstructions) {
        if (!CollectionUtils.isEmpty(newFluxInstructions)) {
            FluxInstruction changedInstruction = fluxOrder.getInstructChain().insertAfterNode(fluxInstruction, newFluxInstructions);
            fluxInstructionRepository.reStore(changedInstruction);
            newFluxInstructions.forEach(fluxInstructionRepository::store);
        }
    }
}
