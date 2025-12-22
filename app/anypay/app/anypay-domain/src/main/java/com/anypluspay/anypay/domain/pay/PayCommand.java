package com.anypluspay.anypay.domain.pay;

import com.anypluspay.anypay.types.common.PayCommandStatus;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * 支付指令
 *
 * @author wxj
 * 2025/12/11
 */
@Data
public class PayCommand extends Entity {

    /**
     * 支付单号
     */
    private String payId;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 状态
     */
    private PayCommandStatus status;

    /**
     * 渠道ID
     */
    private String channelId;
}
