package com.anypluspay.channel;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxj
 * 2024/7/3
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@MapperScan("com.anypluspay.channel.infra.persistence.mapper")
@ComponentScan(basePackages = {"com.anypluspay.channel", "com.anypluspay.channelgateway"})
public class ChannelApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(ChannelApplicationLauncher.class, args);
        log.info("channel application start success");
    }
}
