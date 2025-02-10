package com.anypluspay.payment.domain.flux.engine.processor.impl;

import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.InstructStatus;
import com.anypluspay.payment.domain.flux.engine.processor.InstructResultProcessor;
import com.anypluspay.payment.domain.flux.service.FluxOrderDomainService;
import com.anypluspay.payment.domain.repository.FluxInstructionRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author wxj
 * 2024/1/29
 */
@Service
public class InstructResultProcessorImpl implements InstructResultProcessor {

    @Autowired
    private FluxInstructionRepository instructionRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private FluxOrderDomainService fluxOrderDomainService;

    @Override
    public boolean process(FluxOrder fluxOrder, FluxInstruction fluxInstruction, FluxResult fluxResult) {
        boolean isContinue = true;
        fluxInstruction.setStatus(convertToInstructStatus(fluxResult.getStatus()));
        switch (fluxResult.getStatus()) {
            case SUCCESS:
                successProcess(fluxOrder, fluxInstruction, fluxResult);
                break;
            case FAIL:
                failProcess(fluxOrder, fluxInstruction, fluxResult);
            default:
                isContinue = false;
                break;
        }
        instructionRepository.reStore(fluxInstruction);
        return isContinue;
    }

    private void successProcess(FluxOrder fluxOrder, FluxInstruction fluxInstruction, FluxResult fluxResult) {
        if (!CollectionUtils.isEmpty(fluxResult.getNewFluxInstructions())) {
            for (FluxInstruction newFluxInstruction : fluxResult.getNewFluxInstructions()) {
                newFluxInstruction.setPaymentId(fluxInstruction.getPaymentId());
                newFluxInstruction.setPayOrderId(fluxInstruction.getPayOrderId());
                newFluxInstruction.setFluxOrderId(fluxOrder.getFluxOrderId());
                newFluxInstruction.setInstructionId(idGeneratorService.genIdByRelateId(fluxInstruction.getInstructionId(), IdType.FLUX_INSTRUCT_ID));
                newFluxInstruction.setStatus(InstructStatus.INIT);
            }
            fluxOrderDomainService.insertInstruct(fluxOrder, fluxInstruction, fluxResult.getNewFluxInstructions());
        }
    }

    private void failProcess(FluxOrder fluxOrder, FluxInstruction fluxInstruction, FluxResult fluxResult) {

    }

    private InstructStatus convertToInstructStatus(PayStatus payStatus) {
        return switch (payStatus) {
            case SUCCESS -> InstructStatus.SUCCESS;
            case FAIL -> InstructStatus.FAIL;
            case PROCESS -> InstructStatus.PROCESS;
        };
    }
}
