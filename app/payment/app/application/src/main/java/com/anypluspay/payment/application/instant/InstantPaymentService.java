package com.anypluspay.payment.application.instant;

import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.application.instant.builder.InstantPaymentBuilder;
import com.anypluspay.payment.application.instant.builder.RefundOrderBuilder;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.domain.payorder.refund.RefundOrder;
import com.anypluspay.payment.facade.InstantPaymentFacade;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.request.RefundRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.facade.response.RefundResponse;
import com.anypluspay.payment.types.PayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 直接支付服务
 *
 * @author wxj
 * 2024/1/15
 */
@RestController
public class InstantPaymentService extends AbstractPaymentService implements InstantPaymentFacade {

    @Autowired
    private InstantPaymentBuilder instantPaymentBuilder;

    @Autowired
    private RefundOrderBuilder refundOrderBuilder;

    public InstantPaymentResponse pay(InstantPaymentRequest request) {
        Payment payment = instantPaymentBuilder.buildPayment(request);
        GeneralPayOrder payOrder = instantPaymentBuilder.buildPayOrder(payment.getPaymentId(), request);
        transactionTemplate.executeWithoutResult(status -> {
            paymentRepository.store(payment);
            generalPayOrderRepository.store(payOrder);
        });
        PayResult result = generalPayService.process(payOrder);
        InstantPaymentResponse response = new InstantPaymentResponse();
        response.setPaymentId(payOrder.getPaymentId());
        response.setPayOrderId(payOrder.getOrderId());
        response.setOrderStatus(payOrder.getOrderStatus());
        response.setResult(result);
        return response;
    }

    public RefundResponse refund(RefundRequest request) {
        GeneralPayOrder generalPayOrder = generalPayOrderRepository.load(request.getOrigOrderId());
        RefundOrder refundOrder = transactionTemplate.execute(status -> {
            paymentRepository.lock(generalPayOrder.getPaymentId());
            RefundOrder r = refundOrderBuilder.build(request, generalPayOrder);
            refundOrderRepository.store(r);
            return r;
        });
        PayResult payResult = refundService.process(refundOrder);
        RefundResponse response = new RefundResponse();
        response.setPaymentId(refundOrder.getPaymentId());
        response.setRefundOrderId(refundOrder.getOrderId());
        response.setOrderStatus(refundOrder.getOrderStatus());
        response.setResultCode(payResult.getResultCode());
        response.setResultMessage(payResult.getResultMessage());
        return response;
    }

}
