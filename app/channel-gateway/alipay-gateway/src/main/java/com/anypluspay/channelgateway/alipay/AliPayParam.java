package com.anypluspay.channelgateway.alipay;

import lombok.Data;

/**
 * 支付宝配置参数
 * @author wxj
 * 2024/11/15
 */
@Data
public class AliPayParam {

    /**
     * 支付宝分配给开发者的应用ID
     */
    private String appId;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    private String privateKey;

    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;
}
