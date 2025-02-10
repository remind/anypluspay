package com.anypluspay.payment.domain.flux.engine.processor;

import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;

/**
 * @author wxj
 * 2024/1/29
 */
public interface FluxResultProcessor {

    boolean process(FluxOrder fluxOrder, FluxInstruction lastInstruction, FluxResult lastResult);
}
