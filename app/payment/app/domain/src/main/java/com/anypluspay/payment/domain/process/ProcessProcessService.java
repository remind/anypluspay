package com.anypluspay.payment.domain.process;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.springframework.stereotype.Service;

/**
 * 支付指令处理
 *
 * @author wxj
 * 2025/2/22
 */
@Service
public class ProcessProcessService extends AbstractBaseProcessService {

    public PayResult process(PayProcess payProcess) {
        FluxOrder fluxOrder = buildFluxOrder(payProcess);

        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            payProcess.setStatus(PayProcessStatus.PAYING);
            payProcessRepository.reStore(payProcess);
        });

        PayResult payResult = fluxEngineService.process(fluxOrder);
        processFluxResult(payProcess, payResult);
        return payResult;
    }

    /**
     * 处理交换结果
     *
     * @param payProcess 支付单
     * @param payResult       交换结果
     */
    public void processFluxResult(PayProcess payProcess, PayResult payResult) {
        transactionTemplate.executeWithoutResult(status -> {
            payProcessRepository.lock(payProcess.getProcessId());
            if (payProcess.getStatus() == PayProcessStatus.PAYING) {
                // 仅支付中状态才处理结果，防止重复处理
                payProcess.setStatus(convertStatus(payResult.getPayStatus()));
                payProcess.setResultCode(payResult.getResultCode());
                payProcess.setResultMsg(payResult.getResultMessage());
                payProcessRepository.reStore(payProcess);
                if (payProcess.getStatus() == PayProcessStatus.SUCCESS || payProcess.getStatus() == PayProcessStatus.FAIL) {
                    paymentOrderService.processResult(payProcess.getPaymentId(), payProcess.getStatus() == PayProcessStatus.SUCCESS);
                }
            }
        });
    }

    /**
     * 状态转换
     *
     * @param payStatus 支付状态
     * @return 支付订单状态
     */
    private PayProcessStatus convertStatus(PayStatus payStatus) {
        return switch (payStatus) {
            case SUCCESS -> PayProcessStatus.SUCCESS;
            case FAIL -> PayProcessStatus.FAIL;
            case PROCESS -> PayProcessStatus.PAYING;
        };
    }
}
