package com.anypluspay.anypay.types.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 统一下单请求
 * @author wxj
 * 2026/1/26
 */
@Data
public class UnifiedOrderRequest {

    /**
     * 外部交易
     */
    @NotBlank(message = "外部交易单号不能为空")
    private String outTradeNo;

    /**
     * 支付方式
     */
    @NotBlank(message = "支付方式不能为空")
    private String payWayCode;

    /**
     * 支付金额
     */
    @NotNull(message = "支付金额不能为空")
    @Min(value = 1, message = "支付金额不能为空")
    private Double amount;

    /**
     * 货币代码
     */
    @NotBlank(message = "货币代码不能为空")
    private String currency;
}
