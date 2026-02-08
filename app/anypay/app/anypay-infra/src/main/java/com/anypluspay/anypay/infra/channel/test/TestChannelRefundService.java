package com.anypluspay.anypay.infra.channel.test;

import com.anypluspay.anypay.domain.channel.spi.ChannelRefundService;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2026/2/3
 */
@Service("testChannelRefundService")
public class TestChannelRefundService implements ChannelRefundService {
    @Override
    public ChannelResponse apply(TradeOrder tradeOrder, PayOrder payOrder, PayOrder originPayOrder) {
        return null;
    }
}
