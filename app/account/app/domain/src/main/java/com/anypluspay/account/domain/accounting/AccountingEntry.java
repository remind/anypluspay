package com.anypluspay.account.domain.accounting;

import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会计分录
 * @author wxj
 * 2025/1/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountingEntry extends Entity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 凭证号
     */
    private String voucherNo;

    /**
     * 请求号
     */
    private String requestNo;

    /**
     * 套号，同一套号内借贷平衡
     */
    private String suiteNo;

    /**
     * 账目编码
     */
    private String titleCode;

    /**
     * 帐号
     */
    private String accountNo;

    /**
     * 发生金额
     */
    private Money amount = new Money();

    /**
     * 借贷标志
     */
    private CrDr crDr;

    /**
     * 会计日
     **/
    private String accountingDate;

    /**
     * 备注
     **/
    private String memo;
}
