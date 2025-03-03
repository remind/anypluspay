package com.anypluspay.payment.application.instant.request;

import com.anypluspay.commons.lang.types.Money;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 退款请求
 * @author wxj
 * 2025/2/14
 */
@Data
public class RefundRequest {

    /**
     * 退款请求单号
     */
    private String requestId;

    /**
     * 原请求单号
     */
    private String origRequestId;

    /**
     * 原订单号
     */
    private String origOrderId;

    /**
     * 退款金额
     */
    @NotNull
    private Money amount;

    /**
     * 退款原因
     */
    private String reason;
}
