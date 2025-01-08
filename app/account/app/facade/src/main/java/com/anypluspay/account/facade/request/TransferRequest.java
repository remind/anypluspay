package com.anypluspay.account.facade.request;

import com.anypluspay.commons.lang.types.Money;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 转账请求
 * @author wxj
 * 2025/1/7
 */
@Data
public class TransferRequest {

    /**
     * 转账单号
     **/
    @NotNull
    private String requestNo;

    /**
     * 转出账号
     */
    @NotNull
    private String transferOutAccountNo;

    /**
     * 转入账号
     **/
    @NotNull
    private String transferInAccountNo;

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
