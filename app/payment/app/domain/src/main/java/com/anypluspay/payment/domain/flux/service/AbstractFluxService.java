package com.anypluspay.payment.domain.flux.service;

import com.anypluspay.payment.domain.service.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/1/26
 */
public abstract class AbstractFluxService {

    @Autowired
    protected IdGeneratorService idGeneratorService;
}
