package com.anypluspay.payment.facade.acquiring.pay;

import com.anypluspay.commons.lang.BaseResult;
import lombok.Data;

/**
 * 交易支付响应
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringPayResponse extends BaseResult {

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

    /**
     * 机构跳转参数
     */
    private String ird;

}
