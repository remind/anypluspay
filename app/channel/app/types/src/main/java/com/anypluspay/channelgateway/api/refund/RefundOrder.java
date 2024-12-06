package com.anypluspay.channelgateway.api.refund;

import com.anypluspay.channelgateway.request.OrderInfo;
import com.anypluspay.channel.types.ExtKey;
import lombok.Data;

/**
 * @author wxj
 * 2024/9/16
 */
@Data
public class RefundOrder extends OrderInfo {

    /**
     * 原请求单号
     */
    private String origRequestNo;

    /**
     * 退款原因
     */
    private String reason;

    public String getOrigRequestId() {
        return this.getExtValue(ExtKey.ORIG_INST_REQUEST_NO);
    }
}
