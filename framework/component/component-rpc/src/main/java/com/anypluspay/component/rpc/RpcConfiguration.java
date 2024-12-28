package com.anypluspay.component.rpc;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wxj
 * 2024/12/25
 */
@Configuration
public class RpcConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FacadeExceptionController apiExceptionController() {
        return new FacadeExceptionController();
    }

    @Bean
    public FacadeErrorResponseDecoder errorCoder(ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        return new FacadeErrorResponseDecoder(new SpringDecoder(this.messageConverters, customizers));
    }
}
