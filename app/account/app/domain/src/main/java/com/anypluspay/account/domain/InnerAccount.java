package com.anypluspay.account.domain;

import com.anypluspay.account.domain.utils.AccountUtil;

import com.anypluspay.account.types.enums.AccountFamily;
import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * @author wxj
 * 2023/12/16
 */
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
        this.balance = AccountUtil.changeBalance(this.balance, ioDirection, amount);
    }
}
