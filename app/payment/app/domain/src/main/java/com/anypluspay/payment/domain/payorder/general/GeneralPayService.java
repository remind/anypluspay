package com.anypluspay.payment.domain.payorder.general;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.payorder.AbstractBasePayService;
import com.anypluspay.payment.domain.payorder.event.PayOrderResultEvent;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import org.springframework.stereotype.Service;

/**
 * 普通支付
 *
 * @author wxj
 * 2025/2/22
 */
@Service
public class GeneralPayService extends AbstractBasePayService {

    public PayResult process(GeneralPayOrder generalPayOrder) {
        FluxOrder fluxOrder = buildFluxOrder(generalPayOrder);

        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            generalPayOrder.setOrderStatus(GeneralPayOrderStatus.PAYING);
            generalPayOrderRepository.reStore(generalPayOrder);
        });

        PayResult payResult = fluxEngineService.process(fluxOrder);
        processFluxResult(generalPayOrder, payResult);
        return payResult;
    }

    /**
     * 处理交换结果
     *
     * @param generalPayOrder 支付单
     * @param payResult       交换结果
     */
    public void processFluxResult(GeneralPayOrder generalPayOrder, PayResult payResult) {
        transactionTemplate.executeWithoutResult(status -> {
            paymentRepository.lock(generalPayOrder.getPaymentId());
            if (generalPayOrder.getOrderStatus() == GeneralPayOrderStatus.PAYING) {
                // 仅支付中状态才处理结果，防止重复处理
                generalPayOrder.setOrderStatus(convertStatus(payResult.getPayStatus()));
                generalPayOrderRepository.reStore(generalPayOrder);

                if (generalPayOrder.getOrderStatus() == GeneralPayOrderStatus.SUCCESS
                        || generalPayOrder.getOrderStatus() == GeneralPayOrderStatus.FAIL) {
                    applicationContext.publishEvent(new PayOrderResultEvent(generalPayOrder.getOrderId(), PayOrderType.PAY));
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
    private GeneralPayOrderStatus convertStatus(PayStatus payStatus) {
        return switch (payStatus) {
            case SUCCESS -> GeneralPayOrderStatus.SUCCESS;
            case FAIL -> GeneralPayOrderStatus.FAIL;
            case PROCESS -> GeneralPayOrderStatus.PAYING;
        };
    }
}
