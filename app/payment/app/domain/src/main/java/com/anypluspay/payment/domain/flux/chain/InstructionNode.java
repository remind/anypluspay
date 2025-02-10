package com.anypluspay.payment.domain.flux.chain;

import com.anypluspay.payment.domain.flux.FluxInstruction;
import lombok.Data;

/**
 * 指令节点
 * @author wxj
 * 2025/2/7
 */
@Data
public class InstructionNode {

    /**
     * 指令
     */
    private FluxInstruction fluxInstruction;

    /**
     * 上一个
     */
    private InstructionNode prev;

    /**
     * 下一个
     */
    private InstructionNode next;

    public InstructionNode(FluxInstruction fluxInstruction) {
        this.fluxInstruction = fluxInstruction;
    }

    public void setPrev(InstructionNode prev) {
        this.prev = prev;
        fluxInstruction.setPreInstructionId(prev.getFluxInstruction().getInstructionId());
    }
}
