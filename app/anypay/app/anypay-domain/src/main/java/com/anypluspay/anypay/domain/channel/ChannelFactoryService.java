package com.anypluspay.anypay.domain.channel;

import com.anypluspay.anypay.domain.channel.spi.ChannelCallbackService;
import com.anypluspay.anypay.domain.channel.spi.ChannelRefundService;
import com.anypluspay.anypay.domain.channel.spi.UnifiedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author wxj
 * 2026/1/29
 */
@Service
public class ChannelFactoryService {

    @Autowired
    private ApplicationContext applicationContext;

    public UnifiedOrderService unifiedOrder(String channelCode) {
        UnifiedOrderService unifiedOrderService = applicationContext.getBean(channelCode + "UnifiedOrderService", UnifiedOrderService.class);
        Assert.notNull(unifiedOrderService, "渠道接口不存在");
        return unifiedOrderService;
    }

    public ChannelCallbackService channelCallback(String channelCode) {
        ChannelCallbackService channelCallBackService = applicationContext.getBean(channelCode + "ChannelCallbackService", ChannelCallbackService.class);
        Assert.notNull(channelCallBackService, "渠道接口不存在");
        return channelCallBackService;
    }

    public ChannelRefundService channelRefund(String channelCode) {
        ChannelRefundService channelRefundService = applicationContext.getBean(channelCode + "ChannelRefundService", ChannelRefundService.class);
        Assert.notNull(channelRefundService, "渠道接口不存在");
        return channelRefundService;
    }

}
