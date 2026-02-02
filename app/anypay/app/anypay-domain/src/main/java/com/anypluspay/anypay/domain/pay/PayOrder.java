package com.anypluspay.anypay.domain.pay;

import com.anypluspay.anypay.types.common.PayOrderStatus;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * 支付订单
 *
 * @author wxj
 * 2025/12/11
 */
@Data
public class PayOrder extends Entity {

    /**
     * 支付单号
     */
    private String payId;

    /**
     * 交易单号
     */
    private String tradeId;

    /**
     * 原支付单号
     */
    private String origPayId;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 支付参数
     */
    private String payParam;

    /**
     * 支付金额
     */
    private Money amount;

    /**
     * 支付状态
     */
    private PayOrderStatus status;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 渠道请求单号
     */
    private String channelRequestNo;

    /**
     * 渠道响应单号
     */
    private String channelResponseNo;

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
