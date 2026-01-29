package com.anypluspay.anypay.domain.channel;

import com.anypluspay.anypay.domain.channel.spi.UnifiedOrderService;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author wxj
 * 2026/1/29
 */
@Service
public class ChannelRequestService {

    @Autowired
    private ApplicationContext applicationContext;

    public ChannelResponse unifiedOrder(TradeOrder tradeOrder, PayOrder payOrder) {
        UnifiedOrderService unifiedOrderService = applicationContext.getBean(payOrder.getChannelCode() + "UnifiedOrderService", UnifiedOrderService.class);
        Assert.notNull(unifiedOrderService, "渠道服务不存在");
        return unifiedOrderService.unifiedOrder(tradeOrder, payOrder);
    }
}
