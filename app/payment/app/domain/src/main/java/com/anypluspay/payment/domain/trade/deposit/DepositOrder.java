package com.anypluspay.payment.domain.trade.deposit;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.trade.BaseTradeOrder;
import com.anypluspay.payment.types.trade.DepositOrderStatus;
import lombok.Data;

/**
 * 充值订单
 *
 * @author wxj
 * 2025/5/14
 */
@Data
public class DepositOrder extends BaseTradeOrder {

    /**
     * 发起人会员ID
     */
    private String memberId;

    /**
     * 充值账户
     */
    private String accountNo;

    /**
     * 订单金额
     */
    private Money amount;

    /**
     * 充值状态
     */
    private DepositOrderStatus status;

    /**
     * 订单扩展信息
     */
    private String extension;

    /**
     * 备注
     */
    private String memo;

}
