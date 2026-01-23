package com.anypluspay.anypay.channel.request;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

@Data
public class UnifiedOrderRequest {

    /**
     * 机构请求订单号
     */
    private String instRequestNo;

    /**
     * 金额
     */
    private Money amount;
}
