package com.anypluspay.admin.payment.response.trade;

import lombok.Data;

/**
 * @author wxj
 * 2025/5/19
 */
@Data
public class DepositOrderResponse extends BaseTradeResponse {

    /**
     * 发起人会员ID
     */
    private String memberId;

    /**
     * 充值账户
     */
    private String accountNo;

}
