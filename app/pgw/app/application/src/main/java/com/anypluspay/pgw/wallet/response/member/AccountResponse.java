package com.anypluspay.pgw.wallet.response.member;

import lombok.Data;

/**
 * @author wxj
 * 2025/7/1
 */
@Data
public class AccountResponse {

    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 账户余额
     */
    private BalanceResponse balance;
}
