package com.anypluspay.anypay.openapi.response;

import lombok.Data;

/**
 * @author wxj
 * 2026/1/28
 */
@Data
public class PaySubmitResponse {

    /**
     * 交易单号
     */
    private String tradeId;

    /**
     * 外部交易单号
     */
    private String outTradeNo;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 状态
     */
    private String status;

    /**
     * 结果码
     **/
    private String resultCode;

    /**
     * 结果信息
     **/
    private String resultMsg;

    /**
     * 渠道参数
     */
    private String channelParam;
}
