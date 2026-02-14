package com.anypluspay.anypay.types;

import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import lombok.Data;

/**
 * 支付结果
 *
 * @author wxj
 * 2026/2/8
 */
@Data
public class PayResult {

    /**
     * 订单ID
     */
    private String tradeId;

    /**
     * 订单状态
     */
    private TradeOrderStatus status;

    /**
     * 渠道返回参数
     **/
    private String channelParam;

    /**
     * 结果码
     **/
    private String resultCode;

    /**
     * 结果信息
     **/
    private String resultMsg;
}
