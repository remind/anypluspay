package com.anypluspay.payment.domain.flux.service;

import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;

import java.util.List;

/**
 * @author wxj
 * 2024/1/26
 */
public interface FluxOrderDomainService {

    /**
     * 删除指定指令后面的指令
     * @param fluxOrder
     * @param instructId
     * @return
     */
    void deleteAfterFluxInstruct(FluxOrder fluxOrder, String instructId);


    /**
     * 创建一个指令的逆向指令
     * @param fluxOrder
     * @param fluxInstruction
     * @return
     */
    FluxInstruction createReverseInstruct(FluxOrder fluxOrder, FluxInstruction fluxInstruction);

    /**
     * 在指定指令之后插入指令
     * @param fluxOrder
     * @param fluxInstruction
     * @param newFluxInstructions
     * @return
     */
    void insertInstruct(FluxOrder fluxOrder, FluxInstruction fluxInstruction, List<FluxInstruction> newFluxInstructions);
}
