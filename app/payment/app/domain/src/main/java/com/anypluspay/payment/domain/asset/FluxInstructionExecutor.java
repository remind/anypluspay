package com.anypluspay.payment.domain.asset;

import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;

/**
 * @author wxj
 * 2024/1/26
 */
public interface FluxInstructionExecutor {

    FluxResult execute(FluxOrder fluxOrder, FluxInstruction fluxInstruction);
}
