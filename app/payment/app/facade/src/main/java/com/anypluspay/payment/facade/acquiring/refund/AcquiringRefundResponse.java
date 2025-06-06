package com.anypluspay.payment.facade.acquiring.refund;

import com.anypluspay.commons.lang.BaseResult;
import lombok.Data;

/**
 * 交易退款响应
 *
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringRefundResponse extends BaseResult {

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 合作方ID
     */
    private String partnerId;

    /**
     * 外部交易退款号
     */
    private String outTradeNo;

    /**
     * 订单状态
     */
    private String orderStatus;

}