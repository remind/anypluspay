package com.anypluspay.admin.account.request.register;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 内转内
 * @author wxj
 * 2025/5/9
 */
@Data
public class RegisterRequest {

    /**
     * 借方账户
     */
    @NotBlank
    private String drAccountNo;

    /**
     * 借方资金类型，借方账户为外部账户时必须得有值
     */
    private String drFundType;

    /**
     * 贷方账户
     */
    @NotBlank
    private String crAccountNo;

    /**
     * 贷方资金类型，贷方账户为外部账户时必须得有值
     */
    private String crFundType;

    /**
     * 金额
     */
    @NotBlank
    private BigDecimal amount;

    /**
     * 备注
     */
    private String memo;
}
