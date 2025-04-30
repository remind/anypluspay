package com.anypluspay.payment.domain.payorder.event;

import com.anypluspay.payment.types.PayOrderType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxj
 * 2025/4/10
 */
@Data
@AllArgsConstructor
public class PayOrderResultEvent {

    /**
     * 支付子单ID
     */
    private String payOrderId;

    /**
     * 支付子单类型
     */
    private PayOrderType payOrderType;
}
