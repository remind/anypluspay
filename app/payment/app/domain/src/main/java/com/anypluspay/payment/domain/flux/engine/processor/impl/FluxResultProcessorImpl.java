package com.anypluspay.payment.domain.flux.engine.processor.impl;

import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.*;
import com.anypluspay.payment.domain.flux.engine.processor.FluxResultProcessor;
import com.anypluspay.payment.domain.flux.service.FluxOrderDomainService;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wxj
 * 2024/1/29
 */
@Service
public class FluxResultProcessorImpl implements FluxResultProcessor {

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    @Autowired
    private FluxOrderDomainService fluxOrderDomainService;

    @Override
    public boolean process(FluxOrder fluxOrder, FluxInstruction lastInstruction, FluxResult lastResult) {
        boolean isContinue = false;
        switch (lastResult.getStatus()) {
            case SUCCESS:
                isContinue = successProcess(fluxOrder);
                break;
            case FAIL:
                isContinue = failProcess(fluxOrder, lastInstruction);
                break;
            default:
                fluxOrder.setStatus(FluxOrderStatus.PROCESS);
                break;
        }
        fluxOrderRepository.reStore(fluxOrder);
        return isContinue;
    }

    private boolean successProcess(FluxOrder fluxOrder) {
        long count = fluxOrder.getInstructChain().toList().stream().filter(i -> i.getStatus() != InstructStatus.SUCCESS).count();
        AssertUtil.isTrue(count == 0, "执行成功，但是指令状态不一致");
        fluxOrder.setStatus(FluxOrderStatus.SUCCESS);
        return false;
    }

    private boolean failProcess(FluxOrder fluxOrder, FluxInstruction lastInstruction) {
        boolean isContinue = false;
        if (lastInstruction.getType() == InstructionType.PAY)  {
            // 删除所有后置指令
            fluxOrderDomainService.deleteAfterFluxInstruct(fluxOrder, lastInstruction.getInstructionId());

            // 生成逆向的
            addReverseInstruct(fluxOrder);
            isContinue = true;
            fluxOrder.setStatus(FluxOrderStatus.FAIL);
        } else {
            // TODO 处理逆向失败的，可以重试或者转差错
        }

        return isContinue;
    }

    private void addReverseInstruct(FluxOrder fluxOrder) {
        List<FluxInstruction> forwardInstructs = fluxOrder.getInstructChain().toList().stream()
                .filter(assetFluxInstruct -> assetFluxInstruct.getStatus() == InstructStatus.SUCCESS && assetFluxInstruct.getType() == InstructionType.PAY)
                .toList();
        if (!CollectionUtils.isEmpty(forwardInstructs)) {
            forwardInstructs.forEach(assetFluxInstruct -> {
                fluxOrderDomainService.createReverseInstruct(fluxOrder, assetFluxInstruct);
            });
        }
    }
}
