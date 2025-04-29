package com.anypluspay.channelgateway.api.refund;

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
     * 原机构请求订单号
     */
    private String origInstRequestNo;

    /**
     * 原机构响应订单号
     */
    private String origInstResponseNo;

    /**
     * 退款原因
     */
    private String reason;

}
