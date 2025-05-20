package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.domain.flux.FluxProcess;

import java.util.List;

/**
 * @author wxj
 * 2024/1/27
 */
public interface FluxProcessRepository {

    List<FluxProcess> loadByFluxOrderId(String fluxOrderId);

    FluxProcess loadByPayFundDetailId(String relationId);

    FluxProcess load(String fluxProcessId);

    void store(FluxProcess fluxProcess);

    void reStore(FluxProcess fluxProcess);

    void remove(List<String> fluxProcessIds);
}
