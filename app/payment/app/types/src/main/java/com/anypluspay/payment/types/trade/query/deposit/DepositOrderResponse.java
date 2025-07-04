package com.anypluspay.payment.types.trade.query.deposit;

import com.anypluspay.payment.types.trade.query.BaseTradeResponse;
import lombok.Data;

/**
 * @author wxj
 * 2025/7/4
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

    /**
     * 备注
     */
    private String memo;

}
