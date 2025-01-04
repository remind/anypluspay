package com.anypluspay.account.facade.manager.response;

import com.anypluspay.account.types.enums.BalanceDirection;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2024/12/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AccountResponse extends Entity {

    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 总余额
     */
    private Money balance;

    /**
     * 科目号
     */
    private String titleCode;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 当前余额方向
     */
    private CrDr currentBalanceDirection;

    /**
     * 账户余额方向
     */
    private BalanceDirection balanceDirection;

    /**
     * 币种代码
     */
    private String currencyCode;
}
