package com.anypluspay.payment.domain.process.refund;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.process.AbstractBaseProcessService;
import com.anypluspay.payment.domain.repository.RefundProcessRepository;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退款指令服务
 *
 * @author wxj
 * 2025/2/18
 */
@Service
public class RefundProcessService extends AbstractBaseProcessService {

    @Autowired
    private RefundProcessRepository refundProcessRepository;

    public PayResult process(RefundProcess refundOrder) {
        FluxOrder fluxOrder = buildFluxOrder(refundOrder);
        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            refundOrder.setStatus(RefundOrderStatus.PAYING);
            refundProcessRepository.reStore(refundOrder);
        });

        PayResult payResult = fluxEngineService.process(fluxOrder);
        processFluxResult(refundOrder, payResult);
        return payResult;
    }

    /**
     * 处理 flux 结果
     *
     * @param refundProcess 退款订单
     * @param payResult   支付结果
     */
    public void processFluxResult(RefundProcess refundProcess, PayResult payResult) {
        transactionTemplate.executeWithoutResult(status -> {
            refundProcessRepository.lock(refundProcess.getProcessId());
            if (refundProcess.getStatus() == RefundOrderStatus.PAYING) {
                // 仅支付中状态才处理结果，防止重复处理
                convertStatus(refundProcess, payResult);
                refundProcessRepository.reStore(refundProcess);
                if (refundProcess.getStatus() == RefundOrderStatus.SUCCESS || refundProcess.getStatus() == RefundOrderStatus.FAIL) {
                    paymentOrderService.processResult(refundProcess.getPaymentId(), refundProcess.getStatus() == RefundOrderStatus.SUCCESS);
                }
            }
        });
    }

    /**
     * 状态转换
     *
     * @param refundOrder 退款订单
     * @param payResult   支付结果
     */
    private void convertStatus(RefundProcess refundOrder, PayResult payResult) {
        if (payResult.getPayStatus() == PayStatus.SUCCESS) {
            refundOrder.setStatus(RefundOrderStatus.SUCCESS);
        }
        switch (payResult.getPayStatus()) {
            case SUCCESS:
                refundOrder.setStatus(RefundOrderStatus.SUCCESS);
                break;
            case FAIL:
                refundOrder.setStatus(RefundOrderStatus.FAIL);
                break;
            case PROCESS:
                refundOrder.setStatus(RefundOrderStatus.PAYING);
                break;
        }
    }
}
