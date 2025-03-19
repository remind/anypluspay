package com.anypluspay.payment.facade.response;

import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import com.anypluspay.payment.types.status.OrderStatus;
import com.anypluspay.payment.types.std.GeneralPayOrderStatusDeserializer;
import com.anypluspay.payment.types.std.GeneralPayOrderStatusSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @author remind
 * 2023年07月14日 20:28
 */
@Data
public class InstantPaymentResponse extends BaseResponse {

    /**
     * 支付单号
     */
    private String payOrderId;

    /**
     * 支付单状态
     */
    @JsonSerialize(using = GeneralPayOrderStatusSerializer.class)
    @JsonDeserialize(using = GeneralPayOrderStatusDeserializer.class)
    private GeneralPayOrderStatus orderStatus;

}
