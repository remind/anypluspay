package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.flux.FluxOrder;

/**
 * @author wxj
 * 2024/1/27
 */
public interface FluxOrderRepository {

    void store(FluxOrder fluxOrder);

    void reStore(FluxOrder fluxOrder);

    FluxOrder load(String fluxOrderId);

    FluxOrder lock(String fluxOrderId);

    FluxOrder loadByPayOrderId(String payOrderId);
}
