package com.anypluspay.admin.account.request.register;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 内转内
 * @author wxj
 * 2025/5/9
 */
@Data
public class Inner2InnerRegister {

    /**
     * 借方账号
     */
    private String drAccountNo;

    /**
     * 贷方账号
     */
    private String crAccountNo;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String memo;
}
