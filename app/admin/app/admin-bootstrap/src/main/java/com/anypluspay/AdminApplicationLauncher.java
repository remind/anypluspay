package com.anypluspay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wxj
 * 2024/11/2
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.anypluspay"})
public class AdminApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplicationLauncher.class, args);
        log.info("admin application start success");
    }
}
