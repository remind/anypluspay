package com.anypluspay.payment.domain;

import com.anypluspay.commons.lang.Entity;
import com.anypluspay.payment.types.PaymentType;
import lombok.Data;

/**
 * 支付总单
 *
 * @author wxj
 * 2025/2/23
 */
@Data
public class Payment extends Entity {

    /**
     * 支付总单号
     */
    private String paymentId;

    /**
     * 支付类型
     */
    private PaymentType paymentType;

    /**
     * 发起人用户ID
     */
    private String memberId;

    /**
     * 商户ID
     */
    private String merchantId;
}
