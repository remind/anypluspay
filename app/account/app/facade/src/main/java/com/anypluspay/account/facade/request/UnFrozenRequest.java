package com.anypluspay.account.facade.request;

import com.anypluspay.commons.lang.types.Money;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 解冻请求
 *
 * @author wxj
 * 2025/1/8
 */
@Data
public class UnFrozenRequest {

    /**
     * 请求单号
     **/
    @NotNull
    private String requestNo;

    /**
     * 冻结请求单号，如果有值，则会根据冻结单号进行判断
     */
    private String frozenRequestNo;

    /**
     * 账号
     **/
    @NotNull
    private String accountNo;

    /**
     * 金额，如果传了冻结请求单号，不得超过冻结金额，该值若不传，则会取冻结金额
     **/
    private Money amount;

    /**
     * 备注
     **/
    private String memo;
}
