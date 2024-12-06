package com.anypluspay;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wxj
 * 2024/11/26
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.anypluspay.testbank.persistence.mapper")
public class TestBankApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(TestBankApplicationLauncher.class);
        log.info("test bank start success");
    }
}
