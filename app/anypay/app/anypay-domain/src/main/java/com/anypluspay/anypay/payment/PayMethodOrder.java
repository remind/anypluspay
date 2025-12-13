package com.anypluspay.anypay.payment;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 支付方式订单
 *
 * @author wxj
 * 2025/12/11
 */
@Data
public class PayMethodOrder extends Entity {

    /**
     * 支付单号
     */
    private String paymentId;
}
