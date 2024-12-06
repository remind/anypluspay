package com.anypluspay.channel.infra.channel;

import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/11/27
 */
@Service
public class DubboGatewayProxy {

    public GatewayResult invoke(ChannelApi channelApi, Object content) {
        ChannelGateway channelGateway = getGateway(channelApi);
        GatewayRequest request = new GatewayRequest();
        request.setChannelCode(channelApi.getChannelCode());
        request.setChannelApiType(channelApi.getType());
        request.setContent(content);
        return channelGateway.call(request);
    }

    public ChannelGateway getGateway(ChannelApi channelApi) {
        String[] address = channelApi.getAddress().split(":");
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(address[0]);
        ReferenceConfig<ChannelGateway> reference = new ReferenceConfig<>();
        reference.setApplication(applicationConfig);
        reference.setInterface(ChannelGateway.class);
        reference.setGroup(address[1]);
        return reference.get();
    }
}
