package com.anypluspay.admin.account.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wxj
 * 2025/1/21
 */
@Data
public class TitleDailyDto {

    /**
     * 日期
     */
    private String accountDate;

    /**
     * 科目编号
     */
    private String titleCode;

    /**
     * 科目名称
     */
    private String titleName;

    /**
     * 余额方向
     */
    private String balanceDirection;

    /**
     * 借记金额
     */
    private BigDecimal debitAmount;

    /**
     * 贷记金额
     */
    private BigDecimal creditAmount;

    /**
     * 借记次数
     */
    private Long debitCount;

    /**
     * 贷记次数
     */
    private Long creditCount;

    /**
     * 借记余额
     */
    private BigDecimal debitBalance;

    /**
     * 贷记余额
     */
    private BigDecimal creditBalance;

    /**
     * 币种代码
     */
    private String currencyCode;

}
