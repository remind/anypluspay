package com.anypluspay.account.domain;

import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * @author wxj
 * 2023/12/16
 */
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
        this.availableBalance = AccountUtil.changeBalance(this.availableBalance, ioDirection, amount);
        this.balance = AccountUtil.changeBalance(this.balance, ioDirection, amount);
    }

}
