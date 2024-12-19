package com.anypluspay.channel.infra.config;

import com.anypluspay.channel.domain.SystemConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 系统配置
 * @author wxj
 * 2024/12/8
 */
@Data
@ConfigurationProperties(prefix = "system.config")
@Configuration
public class SystemConfigImpl implements SystemConfig {

    /**
     * 支付结果通知地址
     */
    private String notifyUrl;
}
