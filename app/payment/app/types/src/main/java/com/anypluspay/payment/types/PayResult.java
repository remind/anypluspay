package com.anypluspay.payment.types;

import lombok.Data;

import java.util.Map;

/**
 * @author wxj
 * 2024/1/26
 */
@Data
public class PayResult {

    /**
     * 支付状态
     */
    private PayStatus payStatus;

    /**
     * 支付参数
     */
    private Map<String, String> payParam;

    /**
     * 返回结果码
     */
    private String resultCode;

    /**
     * 返回信息
     */
    private String resultMessage;
}
