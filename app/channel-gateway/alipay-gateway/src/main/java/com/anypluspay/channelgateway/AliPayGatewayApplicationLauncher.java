package com.anypluspay.channelgateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxj
 * 2024/7/3
 */
@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.anypluspay.channelgateway"})
public class AliPayGatewayApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(AliPayGatewayApplicationLauncher.class, args);
        log.info("alipay gateway start success");
    }
}
