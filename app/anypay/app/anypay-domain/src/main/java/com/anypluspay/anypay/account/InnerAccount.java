package com.anypluspay.anypay.account;

import com.anypluspay.anypay.types.account.AccountFamily;
import com.anypluspay.anypay.types.account.AccountResultCode;
import com.anypluspay.anypay.types.account.BalanceDirection;
import com.anypluspay.anypay.types.account.IODirection;
import com.anypluspay.anypay.utils.AccountUtil;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内部账户
 *
 * @author wxj
 * 2023/12/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InnerAccount extends Account {

    /**
     * 备注
     */
    private String memo;

    @Override
    public AccountFamily getAccountFamily() {
        return AccountFamily.INNER;
    }

    public void updateBalance(IODirection ioDirection, Money amount) {
        if (ioDirection == IODirection.OUT && amount.greaterThan(this.getBalance())) {
            if (this.balanceDirection == BalanceDirection.TWO_WAY) {
                this.setBalance(amount.subtract(this.getBalance()));
                this.currentBalanceDirection = this.currentBalanceDirection.reverse();
            } else {
                throw new BizException(AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
            }
        }
        this.setBalance(AccountUtil.changeBalance(this.getBalance(), ioDirection, amount));
    }
}
