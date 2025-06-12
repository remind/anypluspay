package com.anypluspay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 支付网关启动类
 *
 * @author wxj
 * 2025/6/11
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
public class PgwApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(PgwApplicationLauncher.class, args);
        log.info("pgw application start success");
    }
}
