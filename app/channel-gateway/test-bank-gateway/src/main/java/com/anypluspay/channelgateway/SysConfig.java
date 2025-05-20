package com.anypluspay.channelgateway;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 系统配置
 *
 * @author wxj
 * 2025/5/20
 */
@Data
@ConfigurationProperties(prefix = "sys.config")
@Configuration
public class SysConfig {

    /**
     * 银行地址
     */
    private String bankUrl;
}
