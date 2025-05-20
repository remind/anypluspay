package com.anypluspay.payment.domain.biz.deposit;

import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.types.biz.DepositOrderStatus;
import lombok.Data;

/**
 * 充值订单
 *
 * @author wxj
 * 2025/5/14
 */
@Data
public class DepositOrder extends Entity {

    /**
     * 充值单号
     */
    private String paymentId;

    /**
     * 支付指令ID
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
