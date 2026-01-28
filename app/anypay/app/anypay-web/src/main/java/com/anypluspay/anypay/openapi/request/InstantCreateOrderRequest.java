package com.anypluspay.anypay.openapi.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 即时到账下单请求
 *
 * @author wxj
 * 2026/1/26
 */
@Data
public class InstantCreateOrderRequest {

    /**
     * 外部交易
     */
    @NotBlank(message = "外部交易单号不能为空")
    private String outTradeNo;

    /**
     * 收款方
     */
    @NotBlank(message = "收款方ID不能为空")
    private String payeeId;

    /**
     * 收款方账户号
     */
    @NotBlank(message = "收款方账户号不能为空")
    private String payeeAccountNo;

    /**
     * 付款方
     */
    @NotBlank(message = "付款方ID不能为空")
    private String payerId;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String subject;

    /**
     * 商品描述
     */
    @NotBlank(message = "商品描述不能为空")
    private String body;

    /**
     * 支付金额
     */
    @NotNull(message = "支付金额不能为空")
    private String amount;

    /**
     * 货币代码
     */
    @NotBlank(message = "货币代码不能为空")
    private String currency;
}
