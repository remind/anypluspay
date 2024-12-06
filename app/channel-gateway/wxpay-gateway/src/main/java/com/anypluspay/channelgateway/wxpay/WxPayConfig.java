package com.anypluspay.channelgateway.wxpay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxj
 * 2024/9/18
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "secret-key-config.wxpay")
public class WxPayConfig {

    private String appId;

    private String merchantId;

    private String privateKeyFromPath;

    private String merchantSerialNumber;

    private String apiV3Key;
}
