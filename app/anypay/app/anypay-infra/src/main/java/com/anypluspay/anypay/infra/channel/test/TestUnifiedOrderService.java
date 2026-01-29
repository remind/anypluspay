package com.anypluspay.anypay.infra.channel.test;

import cn.hutool.core.lang.UUID;
import com.anypluspay.anypay.domain.channel.spi.UnifiedOrderService;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelOrderStatus;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
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
    public ChannelResponse unifiedOrder(TradeOrder tradeOrder, PayOrder payOrder) {
        ChannelResponse channelResponse = new ChannelResponse();
        channelResponse.setStatus(ChannelOrderStatus.PAYING);
        channelResponse.setChannelRequestNo(payOrder.getChannelRequestNo());
        channelResponse.setChannelResponseNo(UUID.fastUUID().toString(true));
        channelResponse.setChannelParam("channelParam");
        channelResponse.setResultCode("resultCode");
        channelResponse.setResultMsg("resultMsg");
        return channelResponse;
    }
}
