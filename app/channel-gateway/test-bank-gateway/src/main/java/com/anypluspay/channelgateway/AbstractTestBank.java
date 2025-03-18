package com.anypluspay.channelgateway;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author wxj
 * 2024/12/3
 */
public abstract class AbstractTestBank {

    protected static final String  url = "http://localhost:8070";

    protected final WebClient webClient = WebClient.builder().baseUrl(url).build();

}
