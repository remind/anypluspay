package com.anypluspay.anypay.openapi.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wxj
 * 2026/1/27
 */
@Data
public class RefundApplyRequest {

    /**
     * 退款外部交易单号
     */
    @NotBlank(message = "退款外部交易单号不能为空")
    private String outTradeNo;

    /**
     * 原外部交易单号
     */
    @NotBlank(message = "原外部交易单号不能为空")
    private String origOutTradeNo;

    /**
     * 退款金额
     */
    @NotNull(message = "支付金额不能为空")
    private String amount;

}
