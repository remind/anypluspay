package com.anypluspay.payment.domain.asset.accounting;

import com.anypluspay.payment.domain.asset.FluxInstructionExecutor;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.stereotype.Component;

/**
 * 账务指令执行器
 * @author wxj
 * 2024/1/27
 */
@Component("ACCOUNTING_FluxInstructExecutor")
public class AccountingFluxInstructionExecutor implements FluxInstructionExecutor {
    @Override
    public FluxResult execute(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        FluxResult result = new FluxResult();
        result.setStatus(PayStatus.SUCCESS);
        return result;
    }
}
