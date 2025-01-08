package com.anypluspay.account.facade.request;

import com.anypluspay.commons.lang.types.Money;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wxj
 * 2025/1/8
 */
@Data
public class FrozenRequest {

    /**
     * 请求单号
     **/
    @NotNull
    private String requestNo;

    /**
     * 账号
     **/
    @NotNull
    private String accountNo;

    /**
     * 金额
     **/
    @NotNull
    private Money amount;

    /**
     * 备注
     **/
    private String memo;
}
