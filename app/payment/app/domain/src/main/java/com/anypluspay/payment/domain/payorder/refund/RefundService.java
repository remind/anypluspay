package com.anypluspay.payment.domain.payorder.refund;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.payorder.AbstractBasePayService;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退款服务
 *
 * @author wxj
 * 2025/2/18
 */
@Service
public class RefundService extends AbstractBasePayService {

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    public PayResult process(RefundOrder refundOrder) {
        FluxOrder fluxOrder = buildFluxOrder(refundOrder);
        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            refundOrder.setOrderStatus(RefundOrderStatus.PAYING);
            refundOrderRepository.reStore(refundOrder);
        });

        PayResult payResult = fluxEngineService.process(fluxOrder);
        processFluxResult(refundOrder, payResult);
        return payResult;
    }

    /**
     * 处理 flux 结果
     *
     * @param refundOrder 退款订单
     * @param payResult   支付结果
     */
    public void processFluxResult(RefundOrder refundOrder, PayResult payResult) {
        transactionTemplate.executeWithoutResult(status -> {
            paymentRepository.lock(refundOrder.getPaymentId());
            if (refundOrder.getOrderStatus() == RefundOrderStatus.PAYING) {
                // 仅支付中状态才处理结果，防止重复处理
                convertStatus(refundOrder, payResult);
                refundOrderRepository.reStore(refundOrder);
                if (refundOrder.getOrderStatus() == RefundOrderStatus.SUCCESS || refundOrder.getOrderStatus() == RefundOrderStatus.FAIL) {
                    paymentOrderService.processResult(refundOrder.getPaymentId(), refundOrder.getOrderStatus() == RefundOrderStatus.SUCCESS);
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
    private void convertStatus(RefundOrder refundOrder, PayResult payResult) {
        if (payResult.getPayStatus() == PayStatus.SUCCESS) {
            refundOrder.setOrderStatus(RefundOrderStatus.SUCCESS);
        }
        switch (payResult.getPayStatus()) {
            case SUCCESS:
                refundOrder.setOrderStatus(RefundOrderStatus.SUCCESS);
                break;
            case FAIL:
                refundOrder.setOrderStatus(RefundOrderStatus.FAIL);
                break;
            case PROCESS:
                refundOrder.setOrderStatus(RefundOrderStatus.PAYING);
                break;
        }
    }
}
