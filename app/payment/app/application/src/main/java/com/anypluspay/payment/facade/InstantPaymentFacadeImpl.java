package com.anypluspay.payment.facade;


import com.anypluspay.payment.application.instant.InstantPaymentBuilder;
import com.anypluspay.payment.application.instant.InstantPaymentService;
import com.anypluspay.payment.domain.instant.InstantPayment;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.PayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/1/15
 */
@Service
public class InstantPaymentFacadeImpl implements InstantPaymentFacade {

    @Autowired
    private InstantPaymentBuilder instantPaymentBuilder;

    @Autowired
    private InstantPaymentService instantPaymentService;

    @Override
    public InstantPaymentResponse pay(InstantPaymentRequest request) {
        InstantPayment instantPayment = instantPaymentBuilder.build(request);
        PayResult result = instantPaymentService.pay(instantPayment);
        InstantPaymentResponse response = new InstantPaymentResponse();
        response.setPaymentId(instantPayment.getPaymentId());
        response.setPayOrderId(instantPayment.getPayOrder().getOrderId());
        response.setOrderStatus(instantPayment.getPayOrder().getOrderStatus());
        response.setResult(result);
        return response;
    }
}
