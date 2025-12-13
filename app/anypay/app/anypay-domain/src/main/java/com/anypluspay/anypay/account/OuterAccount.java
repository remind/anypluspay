package com.anypluspay.anypay.account;

import com.anypluspay.anypay.types.account.AccountAttribute;
import com.anypluspay.anypay.types.account.AccountFamily;
import com.anypluspay.anypay.types.account.DenyStatus;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 外部账户
 *
 * @author wxj
 * 2023/12/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OuterAccount extends Account {

    /**
     * 会员ID
     */
    private String memberId;
    /**
     * 可用余额
     */
    private Money availableBalance = new Money();
    /**
     * 账户属性
     */
    private AccountAttribute accountAttribute;
    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 冻结状态
     */
    private DenyStatus denyStatus;

    @Override
    public AccountFamily getAccountFamily() {
        return AccountFamily.OUTER;
    }

}
