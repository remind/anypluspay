package com.anypluspay.admin.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 系统配置
 *
 * @author wxj
 * 2025/6/12
 */
@Data
@ConfigurationProperties(prefix = "system")
@Configuration
public class SystemConfig {

    /**
     * pgw地址
     */
    private String pgwAddress;
}
