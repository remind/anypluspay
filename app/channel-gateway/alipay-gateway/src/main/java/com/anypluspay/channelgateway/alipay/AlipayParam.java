package com.anypluspay.channelgateway.alipay;

import lombok.Data;

/**
 * 支付宝配置参数
 *
 * @author wxj
 * 2024/11/15
 */
@Data
public class AlipayParam {

    /**
     * 环境，沙箱、正式
     */
    private String env;

    /**
     * 支付宝分配给开发者的应用ID
     */
    private String appId;

    /**
     * 签名方式
     */
    private String signType;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    private String privateKey;

    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;
}
