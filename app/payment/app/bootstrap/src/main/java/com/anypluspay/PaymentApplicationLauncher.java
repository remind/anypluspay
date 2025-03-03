package com.anypluspay;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.anypluspay.payment.infra.persistence.mapper")
public class PaymentApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplicationLauncher.class);
        log.info("payment start success");
    }
}
