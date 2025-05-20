package com.anypluspay.channelgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author wxj
 * 2024/12/3
 */
public abstract class AbstractTestBank {

    @Autowired
    protected SysConfig sysConfig;

    protected static WebClient webClient = null;

    protected WebClient getWebClient() {
        if (webClient == null) {
            webClient = WebClient.builder().baseUrl(sysConfig.getBankUrl()).build();
        }
        return webClient;
    }
}
