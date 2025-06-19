package com.anypluspay.payment.domain.asset.external.apply;

import com.anypluspay.payment.domain.asset.external.ExternalResultService;
import com.anypluspay.payment.domain.repository.*;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2025/6/19
 */
public abstract class AbstractExternalApplyService {

    @Autowired
    protected AcquiringOrderRepository acquiringOrderRepository;

    @Autowired
    protected DepositOrderRepository depositOrderRepository;

    @Autowired
    protected WithdrawOrderRepository withdrawOrderRepository;

    @Autowired
    protected FundDetailRepository fundDetailRepository;

    @Autowired
    protected FluxProcessRepository fluxProcessRepository;

    @Autowired
    protected IdGeneratorService idGeneratorService;

    @Autowired
    protected ExternalResultService externalResultService;

}
