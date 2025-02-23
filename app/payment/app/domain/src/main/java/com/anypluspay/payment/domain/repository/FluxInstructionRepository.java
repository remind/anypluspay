package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.flux.FluxInstruction;

import java.util.List;

/**
 * @author wxj
 * 2024/1/27
 */
public interface FluxInstructionRepository {

    List<FluxInstruction> loadByFluxOrderId(String fluxOrderId);

    FluxInstruction loadByPayFundDetailId(String relationId);

    FluxInstruction load(String fluxInstructionId);

    void store(FluxInstruction fluxInstruction);

    void reStore(FluxInstruction fluxInstruction);

    void remove(List<String> fluxInstructionIds);
}
