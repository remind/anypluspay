package com.anypluspay.anypay.domain.channel.spi;


import com.anypluspay.anypay.domain.channel.spi.response.ChannelUnifiedOrderResponse;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.trade.TradeOrder;

/**
 * 创建订单
 */
public interface UnifiedOrderService {

    ChannelUnifiedOrderResponse create(TradeOrder tradeOrder, PayOrder payOrder);

}
