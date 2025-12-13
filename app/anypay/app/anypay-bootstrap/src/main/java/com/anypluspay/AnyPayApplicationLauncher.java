package com.anypluspay;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxj
 * 2024/12/24
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.anypluspay.anypay.infra.persistence.mapper")
@ComponentScan
public class AnyPayApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(AnyPayApplicationLauncher.class, args);
        log.info("anypay application start success");
    }
}
