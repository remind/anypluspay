package com.anypluspay.payment.domain.payorder;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.payorder.service.AbstractBasePayService;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2025/2/22
 */
@Service
public class PayService extends AbstractBasePayService {

    public PayResult process(GeneralPayOrder generalPayOrder) {
        FluxOrder fluxOrder = buildFluxOrder(generalPayOrder);

        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            generalPayOrder.setOrderStatus(PayOrderStatus.PAYING);
            generalPayOrderRepository.reStore(generalPayOrder);
        });

        PayResult payResult = fluxEngineService.process(fluxOrder);
        processFluxResult(generalPayOrder, payResult);
        return payResult;
    }

    /**
     * 处理交换结果
     *
     * @param generalPayOrder  支付单
     * @param payResult 交换结果
     */
    public void processFluxResult(GeneralPayOrder generalPayOrder, PayResult payResult) {
        transactionTemplate.executeWithoutResult(status -> {
            paymentRepository.lock(generalPayOrder.getPaymentId());
            if (generalPayOrder.getOrderStatus() == PayOrderStatus.PAYING) {
                // 仅支付中状态才处理结果，防止重复处理
                convertStatus(generalPayOrder, payResult);
                generalPayOrderRepository.reStore(generalPayOrder);
            }
        });
    }

    /**
     * 状态转换
     *
     * @param generalPayOrder  支付订单
     * @param payResult 支付结果
     */
    private void convertStatus(GeneralPayOrder generalPayOrder, PayResult payResult) {
        if (payResult.getPayStatus() == PayStatus.SUCCESS) {
            generalPayOrder.setOrderStatus(PayOrderStatus.SUCCESS);
        }
        switch (payResult.getPayStatus()) {
            case SUCCESS:
                generalPayOrder.setOrderStatus(PayOrderStatus.SUCCESS);
                break;
            case FAIL:
                generalPayOrder.setOrderStatus(PayOrderStatus.FAIL);
                break;
            case PROCESS:
                generalPayOrder.setOrderStatus(PayOrderStatus.PAYING);
                break;
        }
    }
}
