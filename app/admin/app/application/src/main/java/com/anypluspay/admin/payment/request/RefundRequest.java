package com.anypluspay.admin.payment.request;

import lombok.Data;

/**
 * 退款申请请求
 *
 * @author wxj
 * 2025/5/23
 */
@Data
public class RefundRequest {

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 金额
     */
    private String amount;
}
