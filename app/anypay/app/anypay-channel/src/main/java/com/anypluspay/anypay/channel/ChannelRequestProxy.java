package com.anypluspay.anypay.channel;

import com.anypluspay.anypay.channel.request.ChannelUnifiedOrderRequest;
import com.anypluspay.anypay.channel.response.ChannelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 渠道请求代理
 *
 * @author wxj
 * 2026/1/26
 */
@Service
public class ChannelRequestProxy {

    @Autowired
    private ApplicationContext applicationContext;

    public ChannelResponse unifiedOrder(String instCode, ChannelUnifiedOrderRequest request) {
        UnifiedOrderService unifiedOrderService = applicationContext.getBean(instCode + "UnifiedOrderService", UnifiedOrderService.class);
        Assert.notNull(unifiedOrderService, "渠道服务不存在");
        return unifiedOrderService.unifiedOrder(request);
    }
}
