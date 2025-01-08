package com.anypluspay.account.domain.detail;

import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2023/12/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AccountDetail extends Entity {
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
