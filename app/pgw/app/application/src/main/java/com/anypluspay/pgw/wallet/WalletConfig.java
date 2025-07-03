package com.anypluspay.pgw.wallet;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 钱包配置
 * @author wxj
 * 2025/7/3
 */
@Data
@ConfigurationProperties(prefix = "wallet")
@Configuration
public class WalletConfig {

    /**
     * 商户号，可用于充值、提现
     */
    private String partnerId;
}
