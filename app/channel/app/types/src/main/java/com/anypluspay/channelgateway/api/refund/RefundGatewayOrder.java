package com.anypluspay.channelgateway.api.refund;

import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channelgateway.request.GatewayOrder;
import lombok.Data;

/**
 * @author wxj
 * 2024/9/16
 */
@Data
public class RefundGatewayOrder extends GatewayOrder {

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 原机构请求订单号
     */
    public String getOrigInstRequestNo() {
        return getExtValue(ExtKey.ORIG_INST_REQUEST_NO);
    }

    /**
     * 原机构响应订单号
     */
    public String getOrigInstResponseNo() {
        return getExtValue(ExtKey.ORIG_INST_RESPONSE_NO);
    }
}
