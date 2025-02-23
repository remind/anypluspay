package com.anypluspay.payment.facade;

import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.request.RefundRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.facade.response.RefundResponse;

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

    /**
     * 退款
     * @param request
     * @return
     */
    RefundResponse refund(RefundRequest request);
}
