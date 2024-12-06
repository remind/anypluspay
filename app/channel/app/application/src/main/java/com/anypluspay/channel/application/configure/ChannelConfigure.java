package com.anypluspay.channel.application.configure;

import com.anypluspay.channel.application.institution.gateway.GatewayRequestAdvice;
import com.anypluspay.channel.types.channel.ChannelApiType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxj
 * 2024/7/18
 */
@Configuration
public class ChannelConfigure {

    @Bean("gatewayInterceptorMap")
    public Map<ChannelApiType, GatewayRequestAdvice> gatewayInterceptorMap(List<GatewayRequestAdvice> gatewayRequestAdvices) {
        Map<ChannelApiType, GatewayRequestAdvice> gatewayInterceptorMap = new HashMap<>();
        gatewayRequestAdvices.forEach(
                gatewayRequestAdvice -> gatewayInterceptorMap.put(gatewayRequestAdvice.supportApiType(), gatewayRequestAdvice)
        );
        return gatewayInterceptorMap;
    }

}
