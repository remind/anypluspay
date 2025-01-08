package com.anypluspay.account.domain;

import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.types.AccountResultCode;
import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2023/12/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OuterSubAccount extends Entity {

    /**
     * 账户号
     **/
    private String accountNo;

    /**
     * 资金类型
     */
    private String fundType;

    /**
     * 余额
     **/
    private Money balance = new Money();

    /**
     * 可用余额
     */
    private Money availableBalance = new Money();

    /**
     * 备注
     **/
    private String memo;

    /**
     * 修改可用余额
     *
     * @param ioDirection
     * @param amount
     */
    public void updateAvailableBalance(IODirection ioDirection, Money amount) {
        if (ioDirection == IODirection.OUT) {
            AssertUtil.isFalse(amount.greaterThan(this.availableBalance)
                    , AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
        }
        this.availableBalance = AccountUtil.changeBalance(this.availableBalance, ioDirection, amount);
        this.balance = AccountUtil.changeBalance(this.balance, ioDirection, amount);
    }

    /**
     * 冻结余额
     *
     * @param amount 冻结金额
     */
    public void frozenBalance(Money amount) {
        AssertUtil.isFalse(amount.greaterThan(this.availableBalance)
                , AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
        this.availableBalance = AccountUtil.changeBalance(this.availableBalance, IODirection.OUT, amount);
    }

    /**
     * 解冻余额
     *
     * @param amount 解冻金额
     */
    public void unfrozenBalance(Money amount) {
        AssertUtil.isFalse(amount.greaterThan(this.getFrozenBalance())
                , AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
        this.availableBalance = AccountUtil.changeBalance(this.availableBalance, IODirection.IN, amount);
    }

    /**
     * 获取冻结余额
     *
     * @return 返回冻结金额
     */
    public Money getFrozenBalance() {
        return this.balance.subtract(this.availableBalance);
    }

}
