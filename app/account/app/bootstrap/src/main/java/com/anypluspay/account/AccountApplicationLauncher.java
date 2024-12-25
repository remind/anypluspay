package com.anypluspay.account;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxj
 * 2024/12/24
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@MapperScan("com.anypluspay.account.infra.persistence.mapper")
@ComponentScan
public class AccountApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplicationLauncher.class, args);
        log.info("account application start success");
    }
}
