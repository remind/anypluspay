package com.anypluspay.channelgateway;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxj
 * 2024/11/27
 */
@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.anypluspay.channelgateway"})
@EnableDubbo
public class TestBankGatewayApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(TestBankGatewayApplicationLauncher.class, args);
        log.info("test bank gateway start success");
    }
}
