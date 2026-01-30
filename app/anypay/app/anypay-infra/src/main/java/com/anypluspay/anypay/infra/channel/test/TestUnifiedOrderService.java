package com.anypluspay.anypay.infra.channel.test;

import cn.hutool.core.lang.UUID;
import com.anypluspay.anypay.domain.channel.spi.UnifiedOrderService;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelOrderStatus;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelUnifiedOrderResponse;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2026/1/29
 */
@Service("testUnifiedOrderService")
public class TestUnifiedOrderService implements UnifiedOrderService {

    @Override
    public ChannelUnifiedOrderResponse create(TradeOrder tradeOrder, PayOrder payOrder) {
        ChannelUnifiedOrderResponse channelUnifiedOrderResponse = new ChannelUnifiedOrderResponse();
        channelUnifiedOrderResponse.setStatus(ChannelOrderStatus.PAYING);
        channelUnifiedOrderResponse.setChannelRequestNo(payOrder.getChannelRequestNo());
        channelUnifiedOrderResponse.setChannelResponseNo(UUID.fastUUID().toString(true));
        channelUnifiedOrderResponse.setChannelParam("channelParam");
        channelUnifiedOrderResponse.setResultCode("resultCode");
        channelUnifiedOrderResponse.setResultMsg("resultMsg");
        return channelUnifiedOrderResponse;
    }
}
