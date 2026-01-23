package com.anypluspay.anypay.channel;

import com.anypluspay.anypay.channel.request.UnifiedOrderRequest;
import com.anypluspay.anypay.channel.response.ChannelResponse;

/**
 * 创建订单
 */
public interface UnifiedOrderService {

    ChannelResponse unifiedOrder(UnifiedOrderRequest request);

}
