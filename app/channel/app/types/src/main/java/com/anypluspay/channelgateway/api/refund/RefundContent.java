package com.anypluspay.channelgateway.api.refund;

import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channelgateway.request.NormalContent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2024/9/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RefundContent extends NormalContent {

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
