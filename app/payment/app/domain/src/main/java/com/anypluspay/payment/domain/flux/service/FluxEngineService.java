package com.anypluspay.payment.domain.flux.service;

import com.anypluspay.payment.domain.asset.FluxExecutor;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.FluxOrderStatus;
import com.anypluspay.payment.domain.flux.InstructStatus;
import com.anypluspay.payment.domain.flux.service.FluxService;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交换引擎
 * @author wxj
 * 2024/1/27
 */
@Service
public class FluxEngineService {

    @Autowired
    private FluxExecutor fluxExecutor;

    @Autowired
    private FluxService fluxService;

    /**
     * 处理交换单
     *
     * @param fluxOrder 交换订单
     * @return
     */
    public PayResult process(FluxOrder fluxOrder) {
        FluxResult fluxResult = execute(fluxOrder);
        return convertToPayResult(fluxResult);
    }

    private FluxResult execute(FluxOrder fluxOrder) {
        FluxInstruction executeInstruction = fluxOrder.getInstructChain().getExecuteFluxInstruct();
        FluxResult fluxResult = null;
        FluxResult retFluxResult = null;
        while (executeInstruction != null) {
            fluxResult = fluxExecutor.execute(fluxOrder, executeInstruction);
            fluxResult.setExecuteInstruction(executeInstruction);
            fluxService.process(fluxOrder, executeInstruction, fluxResult);

            executeInstruction = executeInstruction.getStatus() != InstructStatus.PROCESS ? fluxOrder.getInstructChain().getExecuteFluxInstruct() : null;
            if (fluxOrder.getStatus() == FluxOrderStatus.FAIL && executeInstruction != null && retFluxResult == null) {
                // 有逆向指令还要执行，则返回最后失败的结果
                retFluxResult = fluxResult;
            }
        }
        return retFluxResult != null ? retFluxResult : fluxResult;
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
