package com.anypluspay.payment.application.notify;

import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.asset.external.ExternalResultService;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.service.FluxEngineService;
import com.anypluspay.payment.domain.flux.service.FluxService;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.domain.payorder.general.GeneralPayService;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.repository.GeneralPayOrderRepository;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 支付结果通知
 *
 * @author wxj
 * 2025/2/23
 */
@Service
public class PayNotifyService {

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    @Autowired
    private ExternalResultService externalResultService;

    @Autowired
    private FluxService fluxService;

    @Autowired
    private FluxEngineService fluxEngineService;

    @Autowired
    private GeneralPayService generalPayService;

    @Autowired
    private GeneralPayOrderRepository generalPayOrderRepository;

    public void process(FundResult fundResult) {
        FluxOrder fluxOrder = fluxOrderRepository.loadByInstructionId(fundResult.getRequestId());
        if (fluxOrder == null) {
            return;
        }
        FluxInstruction fluxInstruction = fluxOrder.getInstructChain().find(fundResult.getRequestId()).getFluxInstruction();
        FluxResult result = externalResultService.process(fluxInstruction, fundResult);
        result.setExecuteInstruction(fluxInstruction);
        fluxService.process(fluxOrder, fluxInstruction, result);
        PayResult payResult = fluxEngineService.process(fluxOrder);
        if (payResult.getPayStatus() == PayStatus.SUCCESS || payResult.getPayStatus() == PayStatus.FAIL) {
            GeneralPayOrder generalPayOrder = generalPayOrderRepository.load(fluxOrder.getPayOrderId());
            generalPayService.processFluxResult(generalPayOrder, payResult);
        }
    }
}
