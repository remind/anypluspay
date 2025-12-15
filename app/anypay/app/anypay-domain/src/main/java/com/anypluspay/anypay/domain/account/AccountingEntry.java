package com.anypluspay.anypay.domain.account;

import com.anypluspay.anypay.types.account.CrDr;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * 会计分录
 *
 * @author wxj
 * 2025/1/8
 */
@Data
public class AccountingEntry {

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

}
