package com.anypluspay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxj
 * 2024/7/3
 */
@SpringBootApplication
@Slf4j
@ComponentScan
@EnableDiscoveryClient
@EnableFeignClients
public class AlipayGatewayApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(AlipayGatewayApplicationLauncher.class, args);
        log.info("alipay gateway start success");
    }
}
