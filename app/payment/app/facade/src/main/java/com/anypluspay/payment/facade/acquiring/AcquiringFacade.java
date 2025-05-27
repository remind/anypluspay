package com.anypluspay.payment.facade.acquiring;

import com.anypluspay.payment.facade.ApiConstants;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateRequest;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateResponse;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayRequest;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayResponse;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundRequest;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundResponse;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 收单支付
 *
 * @author wxj
 * 2025/5/17
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "AcquiringFacade")
@Validated
public interface AcquiringFacade {
    String PREFIX = "/acquiring";

    /**
     * 交易创建
     *
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/create")
    AcquiringCreateResponse create(@RequestBody @Validated AcquiringCreateRequest request);

    /**
     * 交易支付
     *
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/pay")
    AcquiringPayResponse pay(@RequestBody @Validated AcquiringPayRequest request);

    /**
     * 交易退款
     *
     * @param request
     * @return
     */
    @PostMapping(PREFIX + "/refund")
    AcquiringRefundResponse refund(@RequestBody @Validated AcquiringRefundRequest request);

    /**
     * 查询交易
     *
     * @param paymentId
     * @return
     */
    @GetMapping(PREFIX + "/query-by-payment-id")
    TradeResponse queryByPaymentId(@RequestParam @Length(min = 6, max = 32, message = "支付单号长度为6-32") String paymentId);

    /**
     * 查询交易
     *
     * @param partnerId
     * @param outTradeNo
     * @return
     */
    @GetMapping(PREFIX + "/query-by-partner")
    TradeResponse queryByPartner(@RequestParam @Length(min = 6, max = 64, message = "合作方长度为6-64") String partnerId,
                                 @RequestParam @Length(min = 6, max = 64, message = "外部订单号长度为6-64") String outTradeNo);
}
