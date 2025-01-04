package com.anypluspay.account.facade.manager.response;

import com.anypluspay.account.types.enums.AccountAttribute;
import com.anypluspay.account.types.enums.DenyStatus;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 外部户详情
 *
 * @author wxj
 * 2024/12/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OuterAccountResponse extends AccountResponse {

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 可用余额
     */
    private Money availableBalance;

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
}
