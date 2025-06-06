package com.anypluspay.payment.facade.instant;

import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.facade.instant.builder.InstantPaymentBuilder;
import com.anypluspay.payment.facade.instant.builder.RefundOrderBuilder;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.pay.refund.RefundOrder;
import com.anypluspay.payment.facade.InstantPaymentFacade;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.request.RefundRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.facade.response.RefundResponse;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 直接支付服务
 *
 * @author wxj
 * 2024/1/15
 */
@RestController
public class InstantPaymentFacadeImpl extends AbstractPaymentService implements InstantPaymentFacade {

    @Autowired
    private InstantPaymentBuilder instantPaymentBuilder;

    @Autowired
    private RefundOrderBuilder refundOrderBuilder;

    public InstantPaymentResponse pay(InstantPaymentRequest request) {
        Payment payment = instantPaymentBuilder.buildPayment(request);
        PayOrder payOrder = instantPaymentBuilder.buildPayProcess(payment.getPaymentId(), request);
        transactionTemplate.executeWithoutResult(status -> {
            paymentRepository.store(payment);
            payOrderRepository.store(payOrder);
        });
        PayResult result = payOrderService.process(payOrder);
        InstantPaymentResponse response = new InstantPaymentResponse();
        response.setPaymentId(payOrder.getTradeId());
        response.setPayOrderId(payOrder.getOrderId());
        response.setOrderStatus(payOrder.getStatus());
        response.setResult(result);
        return response;
    }

    public RefundResponse refund(RefundRequest request) {
        PayOrder generalPayOrder = getOrigProcess(request);
        RefundOrder refundOrder = transactionTemplate.execute(status -> {
            paymentRepository.lock(generalPayOrder.getTradeId());
            RefundOrder r = refundOrderBuilder.buildRefundProcess(request, generalPayOrder);
            refundOrderRepository.store(r);
            return r;
        });
        PayResult payResult = refundOrderService.process(refundOrder);
        RefundResponse response = new RefundResponse();
        response.setPaymentId(refundOrder.getTradeId());
        response.setRefundOrderId(refundOrder.getOrderId());
        response.setOrderStatus(refundOrder.getStatus().getCode());
        response.setResultCode(payResult.getResultCode());
        response.setResultMessage(payResult.getResultMessage());
        return response;
    }

    private PayOrder getOrigProcess(RefundRequest request) {
        PayOrder payOrder;
        if (StringUtils.isNotBlank(request.getOrigOrderId())) {
            payOrder = payOrderRepository.load(request.getOrigOrderId());
        } else {
            throw new BizException("无法查到原支付指令");
        }
        AssertUtil.notNull(payOrder, "无法查到原支付指令");
        AssertUtil.isTrue(payOrder.getStatus() == PayProcessStatus.SUCCESS, "原单未成功");
        return payOrder;
    }

}
