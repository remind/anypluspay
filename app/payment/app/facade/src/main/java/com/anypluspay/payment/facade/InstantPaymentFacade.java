package com.anypluspay.payment.facade;

import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.request.RefundRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.facade.response.RefundResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 直接支付
 * @author remind
 * 2023年07月14日 20:27
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "InstantPaymentFacade")
public interface InstantPaymentFacade {

    String PREFIX = "/instant-payment";
    /**
     * 支付
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/pay")
    InstantPaymentResponse pay(@RequestBody InstantPaymentRequest request);

    /**
     * 退款
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/refund")
    RefundResponse refund(@RequestBody RefundRequest request);
}

