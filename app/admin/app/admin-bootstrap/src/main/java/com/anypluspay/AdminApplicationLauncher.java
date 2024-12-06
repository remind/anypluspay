package com.anypluspay;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wxj
 * 2024/11/2
 */
@SpringBootApplication
@Slf4j
@MapperScan({"com.anypluspay.channel.infra.persistence.mapper", "com.anypluspay.admin.dao.mapper"})
public class AdminApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplicationLauncher.class, args);
        log.info("admin application start success");
    }
}
