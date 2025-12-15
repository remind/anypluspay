package com.anypluspay.anypay.domain.payment;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 支付订单
 *
 * @author wxj
 * 2025/12/11
 */
@Data
public class PaymentOrder extends Entity {

    /**
     * 交易单号
     */
    private String tradeId;

    /**
     * 支付单号
     */
    private String paymentId;
}
