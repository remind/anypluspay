package com.anypluspay.pgw.cashier.request;

import lombok.Data;

/**
 * @author wxj
 * 2025/5/15
 */
@Data
public class DepositCashierRequest {

    /**
     * 合作方ID
     */
    private String partnerId;

    /**
     * 充值时的会员ID
     */
    private String memberId;

    /**
     * 充值时的账户号
     */
    private String accountNo;

    /**
     * 充值时的金额
     */
    private String amount;

    /**
     * 充值时的备注
     */
    private String memo;
}
