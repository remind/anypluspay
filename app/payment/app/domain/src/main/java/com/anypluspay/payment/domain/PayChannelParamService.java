package com.anypluspay.payment.domain;

/**
 * 支付渠道参数
 * 一般为渠道生成给到前端跳转到渠道时的参数
 * @author wxj
 * 2025/5/23
 */
public interface PayChannelParamService {

    /**
     * 存储
     * @param payOrderId
     * @param param
     */
    void store(String payOrderId, String param);

    /**
     * 获取
     * @param payOrderId
     */
    String get(String payOrderId);
}
