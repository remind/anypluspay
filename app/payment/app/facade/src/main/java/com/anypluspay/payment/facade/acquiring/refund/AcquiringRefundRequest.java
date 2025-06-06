package com.anypluspay.payment.facade.acquiring.refund;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 交易退款请求
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringRefundRequest {

    /**
     * 合作方ID
     */
    @Length(min = 6, max = 64, message = "合作方长度为6-64")
    private String partnerId;

    /**
     * 外部交易退款号
     */
    @NotBlank(message = "外部交易退款号不能为空")
    @Length(min = 6, max = 64, message = "外部交易退款号长度为6-64")
    private String outTradeNo;

    /**
     * 原交易ID
     */
    @Length(min = 6, max = 32, message = "原交易ID长度为6-32")
    private String origTradeId;

    /**
     * 原外部交易号
     */
    @Length(min = 6, max = 64, message = "原外部交易号长度为6-64")
    private String origOutTradeNo;

    /**
     * 退款金额
     */
    private String amount;
}
