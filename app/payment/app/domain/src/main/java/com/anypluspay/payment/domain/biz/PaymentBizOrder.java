package com.anypluspay.payment.domain.biz;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 支付业务单
 *
 * @author wxj
 * 2025/5/17
 */
@Data
public class PaymentBizOrder extends Entity {

    /**
     * 支付业务单号
     */
    private String paymentId;
}
