package com.anypluspay.payment.domain.flux.engine.impl;

import com.anypluspay.payment.domain.asset.FluxExecutor;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.engine.FluxEngineService;
import com.anypluspay.payment.domain.flux.engine.processor.FluxResultProcessor;
import com.anypluspay.payment.domain.flux.engine.processor.InstructResultProcessor;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/1/27
 */
@Service
@Slf4j
public class FluxEngineServiceImpl implements FluxEngineService {

    @Autowired
    private FluxResultProcessor fluxResultProcessor;

    @Autowired
    private InstructResultProcessor instructResultProcessor;

    @Autowired
    private FluxExecutor fluxExecutor;

    @Override
    public PayResult process(FluxOrder fluxOrder) {
        FluxResult fluxResult = execute(fluxOrder);
        boolean isContinue = fluxResultProcessor.process(fluxOrder, fluxResult.getExecuteInstruction(), fluxResult);
        if (isContinue) {
            // 组合支付，后面的失败了，需要对前面的退款
            execute(fluxOrder);
        }
        return convertToPayResult(fluxResult);
    }

    private FluxResult execute(FluxOrder fluxOrder) {
        FluxInstruction executeInstruction = fluxOrder.getInstructChain().getExecuteFluxInstruct();
        FluxResult fluxResult = null;
        boolean isContinue = true;
        while (executeInstruction != null && isContinue) {
            fluxResult = fluxExecutor.execute(fluxOrder, executeInstruction);
            fluxResult.setExecuteInstruction(executeInstruction);
            isContinue = instructResultProcessor.process(fluxOrder, executeInstruction, fluxResult);
            if (isContinue) {
                executeInstruction = fluxOrder.getInstructChain().getExecuteFluxInstruct();
            }
        }
        return fluxResult;
    }


    private PayResult convertToPayResult(FluxResult fluxResult) {
        PayResult payResult = new PayResult();
        if (fluxResult == null) {
            payResult.setPayStatus(PayStatus.SUCCESS);
        } else {
            payResult.setPayStatus(fluxResult.getStatus());
            payResult.setResultMessage(fluxResult.getResultMessage());
            payResult.setResultCode(fluxResult.getResultCode());
            payResult.setPayParam(fluxResult.getPayParam());
        }
        return payResult;
    }

}
