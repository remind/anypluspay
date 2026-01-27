package com.anypluspay.anypay.domain.pay;

import com.anypluspay.anypay.types.common.PayStatus;
import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 支付订单
 *
 * @author wxj
 * 2025/12/11
 */
@Data
public class PayOrder extends Entity {

    /**
     * 支付单号
     */
    private String payOrderId;

    /**
     * 交易单号
     */
    private String tradeOrderId;

    /**
     * 支付状态
     */
    private PayStatus status;

}
