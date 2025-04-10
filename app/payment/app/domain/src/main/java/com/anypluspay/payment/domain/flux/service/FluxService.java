package com.anypluspay.payment.domain.flux.service;

import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.*;
import com.anypluspay.payment.domain.repository.FluxInstructionRepository;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 交换服务
 * @author wxj
 * 2025/2/22
 */
@Service
public class FluxService {

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    @Autowired
    private FluxInstructionRepository instructionRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private FluxOrderService fluxOrderService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void process(FluxOrder fluxOrder, FluxInstruction fluxInstruction, FluxResult fluxResult) {
        fluxInstruction.setStatus(convertToInstructStatus(fluxResult.getStatus()));
        transactionTemplate.executeWithoutResult(status -> {
            FluxOrder lockFluxOrder = fluxOrderRepository.lock(fluxOrder.getFluxOrderId());

            if (fluxInstruction.getStatus() == InstructStatus.SUCCESS) {
                addNewRelateInstruction(fluxOrder, fluxInstruction, fluxResult.getNewFluxInstructions());

                boolean isEnd = fluxOrder.getInstructChain().getTail().getFluxInstruction().getInstructionId().equals(fluxInstruction.getInstructionId());
                if (isEnd) {
                    successProcess(fluxOrder);
                }
            } else if (fluxInstruction.getStatus() == InstructStatus.FAIL) {
                failProcess(fluxOrder, fluxInstruction);
            }
            instructionRepository.reStore(fluxInstruction);
            if (lockFluxOrder.getStatus() != fluxOrder.getStatus() && lockFluxOrder.getStatus() == FluxOrderStatus.PROCESS) {
                fluxOrderRepository.reStore(fluxOrder);
            }
        });
    }

    /**
     * 成功处理
     *
     * @param fluxOrder
     */
    private void successProcess(FluxOrder fluxOrder) {
        long count = fluxOrder.getInstructChain().toList().stream().filter(i -> i.getStatus() != InstructStatus.SUCCESS).count();
        if (count == 0) {
            fluxOrder.setStatus(FluxOrderStatus.SUCCESS);
        }
    }

    /**
     * 失败处理
     *
     * @param fluxOrder
     * @param lastInstruction
     */
    private void failProcess(FluxOrder fluxOrder, FluxInstruction lastInstruction) {
        if (lastInstruction.getType() == InstructionType.PAY) {
            // 删除所有后置指令
            fluxOrderService.deleteAfterFluxInstruct(fluxOrder, lastInstruction.getInstructionId());
            // 生成成功指令的逆向指令
            addReverseInstruct(fluxOrder);
        }
        fluxOrder.setStatus(FluxOrderStatus.FAIL);
    }

    /**
     * 新增逆向指令
     *
     * @param fluxOrder
     */
    private void addReverseInstruct(FluxOrder fluxOrder) {
        List<FluxInstruction> forwardInstructs = fluxOrder.getInstructChain().toList().stream()
                .filter(assetFluxInstruct -> assetFluxInstruct.getStatus() == InstructStatus.SUCCESS && assetFluxInstruct.getType() == InstructionType.PAY)
                .toList();
        if (!CollectionUtils.isEmpty(forwardInstructs)) {
            forwardInstructs.forEach(assetFluxInstruct -> {
                fluxOrderService.createReverseInstruct(fluxOrder, assetFluxInstruct);
            });
        }
    }

    /**
     * 新增关联指令
     *
     * @param fluxOrder
     * @param fluxInstruction
     * @param newFluxInstructions
     */
    private void addNewRelateInstruction(FluxOrder fluxOrder, FluxInstruction fluxInstruction, List<FluxInstruction> newFluxInstructions) {
        if (!CollectionUtils.isEmpty(newFluxInstructions)) {
            for (FluxInstruction newFluxInstruction : newFluxInstructions) {
                newFluxInstruction.setPaymentId(fluxInstruction.getPaymentId());
                newFluxInstruction.setPayOrderId(fluxInstruction.getPayOrderId());
                newFluxInstruction.setFluxOrderId(fluxOrder.getFluxOrderId());
                newFluxInstruction.setInstructionId(idGeneratorService.genIdByRelateId(fluxInstruction.getInstructionId(), IdType.FLUX_INSTRUCT_ID));
                newFluxInstruction.setStatus(InstructStatus.INIT);
            }
            fluxOrderService.insertInstruct(fluxOrder, fluxInstruction, newFluxInstructions);
        }
    }

    /**
     * 状态转换
     *
     * @param payStatus
     * @return
     */
    private InstructStatus convertToInstructStatus(PayStatus payStatus) {
        return switch (payStatus) {
            case SUCCESS -> InstructStatus.SUCCESS;
            case FAIL -> InstructStatus.FAIL;
            case PROCESS -> InstructStatus.PROCESS;
        };
    }
}
