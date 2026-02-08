package com.anypluspay.anypay.domain.channel.spi;

import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.trade.TradeOrder;

/**
 * @author wxj
 * 2026/2/3
 */
public interface ChannelRefundService {

    ChannelResponse apply(TradeOrder tradeOrder, PayOrder payOrder, PayOrder originPayOrder);
}
