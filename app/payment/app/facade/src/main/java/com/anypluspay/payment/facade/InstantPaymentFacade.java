package com.anypluspay.payment.facade;

import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;

/**
 * 直接支付
 * @author remind
 * 2023年07月14日 20:27
 */
public interface InstantPaymentFacade {

    /**
     * 支付
     * @param request
     * @return
     */
    InstantPaymentResponse pay(InstantPaymentRequest request);
}
