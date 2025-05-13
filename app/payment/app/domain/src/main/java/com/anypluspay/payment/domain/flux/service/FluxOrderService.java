package com.anypluspay.payment.domain.flux.service;

import com.anypluspay.payment.domain.flux.*;
import com.anypluspay.payment.domain.flux.chain.InstructChain;
import com.anypluspay.payment.domain.repository.FluxInstructionRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 交换订单领域服务
 *
 * @author wxj
 * 2024/1/26
 */
@Service
public class FluxOrderService {

    @Autowired
    private FluxInstructionRepository fluxInstructionRepository;

    @Autowired
    protected IdGeneratorService idGeneratorService;

    /**
     * 删除指定指令后面的指令
     *
     * @param fluxOrder
     * @param instructId
     * @return
     */
    public void deleteAfterFluxInstruct(FluxOrder fluxOrder, String instructId) {
        List<String> deleteIds = fluxOrder.getInstructChain().deleteAfterNode(instructId);
        fluxInstructionRepository.remove(deleteIds);
    }


    /**
     * 创建一个指令的逆向指令
     *
     * @param fluxOrder
     * @param fluxInstruction
     * @return
     */
    public FluxInstruction createRevokeInstruct(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        FluxInstruction reverseInstruct = new FluxInstruction();
        InstructChain instructChain = fluxOrder.getInstructChain();
        reverseInstruct.setInstructionId(idGeneratorService.genIdByRelateId(fluxInstruction.getFluxOrderId(), IdType.FLUX_INSTRUCT_ID));
        reverseInstruct.setFluxOrderId(fluxInstruction.getFluxOrderId());
        reverseInstruct.setPayOrderId(fluxInstruction.getPayOrderId());
        reverseInstruct.setPaymentId(fluxInstruction.getPaymentId());
        reverseInstruct.setType(fluxInstruction.getType());
        reverseInstruct.setDirection(InstructionDirection.REVOKE);
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

    /**
     * 在指定指令之后插入指令
     *
     * @param fluxOrder
     * @param fluxInstruction
     * @param newFluxInstructions
     * @return
     */
    public void insertInstruct(FluxOrder fluxOrder, FluxInstruction fluxInstruction, List<FluxInstruction> newFluxInstructions) {
        if (!CollectionUtils.isEmpty(newFluxInstructions)) {
            FluxInstruction changedInstruction = fluxOrder.getInstructChain().insertAfterNode(fluxInstruction, newFluxInstructions);
            fluxInstructionRepository.reStore(changedInstruction);
            newFluxInstructions.forEach(fluxInstructionRepository::store);
        }
    }
}
