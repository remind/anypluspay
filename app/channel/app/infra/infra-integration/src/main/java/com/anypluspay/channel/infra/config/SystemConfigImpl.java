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
@ConfigurationProperties(prefix = "system")
@Configuration
public class SystemConfigImpl implements SystemConfig {

    /**
     * pgw域名
     */
    private String pgwAddress;
}
