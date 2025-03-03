package com.anypluspay.payment.web.controller;

import com.anypluspay.payment.application.instant.InstantPaymentService;
import com.anypluspay.payment.application.instant.request.InstantPaymentRequest;
import com.anypluspay.payment.application.instant.request.RefundRequest;
import com.anypluspay.payment.application.instant.response.InstantPaymentResponse;
import com.anypluspay.payment.application.instant.response.RefundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 直接支付
 * @author wxj
 * 2025/3/3
 */
@RestController
@RequestMapping("/instant")
public class InstantPaymentController {

    @Autowired
    private InstantPaymentService instantPaymentService;

    /**
     * 支付
     * @param request 支付请求
     * @return  支付结果
     */
    @PostMapping("/pay")
    InstantPaymentResponse pay(InstantPaymentRequest request) {
        return instantPaymentService.pay(request);
    }

    /**
     * 退款
     * @param request 退款请求
     * @return  退款结果
     */
    @PostMapping("/refund")
    RefundResponse refund(RefundRequest request) {
        return instantPaymentService.refund(request);
    }
}
