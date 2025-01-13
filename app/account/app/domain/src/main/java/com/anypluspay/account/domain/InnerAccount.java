package com.anypluspay.account.domain;

import com.anypluspay.account.domain.utils.AccountUtil;

import com.anypluspay.account.types.AccountResultCode;
import com.anypluspay.account.types.enums.AccountFamily;
import com.anypluspay.account.types.enums.BalanceDirection;
import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2023/12/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InnerAccount extends Account {

    /**
     * 余额
     */
    private Money balance = new Money();

    /**
     * 备注
     */
    private String memo;

    @Override
    public AccountFamily getAccountFamily() {
        return AccountFamily.INNER;
    }

    public void updateBalance(IODirection ioDirection, Money amount) {
        if (ioDirection == IODirection.OUT && amount.greaterThan(this.balance)) {
            if (this.balanceDirection == BalanceDirection.TWO_WAY) {
                this.balance = amount.subtract(this.balance);
                this.currentBalanceDirection = this.currentBalanceDirection.reverse();
            } else {
                throw new BizException(AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
            }
        }
        this.balance = AccountUtil.changeBalance(this.balance, ioDirection, amount);
    }
}
