package com.anypluspay.payment.facade.instant;

import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.domain.process.PayProcess;
import com.anypluspay.payment.facade.instant.builder.InstantPaymentBuilder;
import com.anypluspay.payment.facade.instant.builder.RefundOrderBuilder;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.process.refund.RefundProcess;
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
        PayProcess payOrder = instantPaymentBuilder.buildPayProcess(payment.getPaymentId(), request);
        transactionTemplate.executeWithoutResult(status -> {
            paymentRepository.store(payment);
            payProcessRepository.store(payOrder);
        });
        PayResult result = payProcessService.process(payOrder);
        InstantPaymentResponse response = new InstantPaymentResponse();
        response.setPaymentId(payOrder.getPaymentId());
        response.setPayOrderId(payOrder.getProcessId());
        response.setOrderStatus(payOrder.getStatus());
        response.setResult(result);
        return response;
    }

    public RefundResponse refund(RefundRequest request) {
        PayProcess generalPayOrder = getOrigProcess(request);
        RefundProcess refundOrder = transactionTemplate.execute(status -> {
            paymentRepository.lock(generalPayOrder.getPaymentId());
            RefundProcess r = refundOrderBuilder.buildRefundProcess(request, generalPayOrder);
            refundOrderRepository.store(r);
            return r;
        });
        PayResult payResult = refundService.process(refundOrder);
        RefundResponse response = new RefundResponse();
        response.setPaymentId(refundOrder.getPaymentId());
        response.setRefundOrderId(refundOrder.getProcessId());
        response.setOrderStatus(refundOrder.getStatus().getCode());
        response.setResultCode(payResult.getResultCode());
        response.setResultMessage(payResult.getResultMessage());
        return response;
    }

    private PayProcess getOrigProcess(RefundRequest request) {
        PayProcess payProcess;
        if (StringUtils.isNotBlank(request.getOrigOrderId())) {
            payProcess = payProcessRepository.load(request.getOrigOrderId());
        } else if (StringUtils.isNotBlank(request.getOrigRequestId())) {
            payProcess = payProcessRepository.loadByRequestId(request.getOrigRequestId());
        } else {
            throw new BizException("无法查到原支付指令");
        }
        AssertUtil.notNull(payProcess, "无法查到原支付指令");
        AssertUtil.isTrue(payProcess.getStatus() == PayProcessStatus.SUCCESS, "原单未成功");
        return payProcess;
    }

}
