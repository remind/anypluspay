package com.anypluspay.anypay.domain.channel.spi;


import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.trade.TradeOrder;

/**
 * 创建订单
 */
public interface UnifiedOrderService {

    ChannelResponse unifiedOrder(TradeOrder tradeOrder, PayOrder payOrder);

}
