package com.anypluspay.payment.domain.flux.engine;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.types.PayResult;

/**
 * @author wxj
 * 2024/1/27
 */
public interface FluxEngineService {

    PayResult process(FluxOrder fluxOrder);
}
