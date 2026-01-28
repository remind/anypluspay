package com.anypluspay.anypay.openapi.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author wxj
 * 2026/1/28
 */
@Data
public class PaySubmitRequest {

    /**
     * 交易单号
     */
    private String tradeId;

    /**
     * 外部交易单号
     */
    private String outTradeNo;

    /**
     * 支付方式
     */
    @NotBlank(message = "支付方式不能为空")
    private String payMethod;

    /**
     * 支付参数
     */
    private String payParam;
}
