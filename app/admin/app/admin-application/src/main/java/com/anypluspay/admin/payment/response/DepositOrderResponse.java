package com.anypluspay.admin.payment.response;

import lombok.Data;

/**
 * @author wxj
 * 2025/5/19
 */
@Data
public class DepositOrderResponse extends AbstractBizOrderResponse {

    /**
     * 支付指令
     */
    private String payProcessId;

    /**
     * 发起人会员ID
     */
    private String memberId;

    /**
     * 充值账户
     */
    private String accountNo;

}
