package com.anypluspay.payment.facade.acquiring.create;

import com.anypluspay.commons.lang.types.Money;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 交易创建请求
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringCreateRequest {
    /**
     * 交易类型
     */
    @NotBlank(message = "交易类型不能为空")
    private String tradeType;

    /**
     * 外部订单号
     */
    @NotBlank(message = "外部订单号不能为空")
    @Length(min = 6, max = 64, message = "外部订单号长度为6-64")
    private String outTradeNo;

    /**
     * 合作方
     */
    @NotBlank(message = "合作方不能为空")
    @Length(min = 6, max = 15, message = "合作方长度为6-15")
    private String partnerId;

    /**
     * 收款方
     */
    @NotBlank(message = "收款方不能为空")
    @Length(min = 6, max = 15, message = "收款方长度为6-15")
    private String payeeId;

    /**
     * 收款方账户号
     */
    @NotBlank(message = "收款方账户号不能为空")
    @Length(min = 6, max = 30, message = "收款方账户号长度为6-30")
    private String payeeAccountNo;

    /**
     * 付款方
     */
    @Length(min = 6, max = 30, message = "付款方长度为6-15")
    private String payerId;

    /**
     * 标题
     */
    @Length(max = 128, message = "标题长度不得超过128")
    private String subject;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private Money amount;

    /**
     * 过期时间，分钟
     */
    @Max(value = 120, message = "过期时间不能超过2小时")
    private Integer expireMinutes;

    /**
     * 扩展信息
     */
    @Length(max = 1024, message = "扩展信息长度不得超过1024")
    private String extension;
}
