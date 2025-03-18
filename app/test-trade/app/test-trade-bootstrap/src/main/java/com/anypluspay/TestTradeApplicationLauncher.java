package com.anypluspay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wxj
 * 2025/3/17
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
public class TestTradeApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(TestTradeApplicationLauncher.class, args);
        log.info("test trade application start success");
    }
}
