package com.anypluspay.anypay.channel.test;

import com.anypluspay.anypay.channel.UnifiedOrderService;
import com.anypluspay.anypay.channel.request.ChannelUnifiedOrderRequest;
import com.anypluspay.anypay.channel.response.ChannelResponse;
import org.springframework.stereotype.Service;

@Service("testUnifiedOrderService")
public class TestUnifiedOrderService implements UnifiedOrderService {
    @Override
    public ChannelResponse unifiedOrder(ChannelUnifiedOrderRequest request) {
        return null;
    }
}
