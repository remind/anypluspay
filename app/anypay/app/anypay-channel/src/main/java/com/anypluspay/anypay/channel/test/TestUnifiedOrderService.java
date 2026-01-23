package com.anypluspay.anypay.channel.test;

import com.anypluspay.anypay.channel.UnifiedOrderService;
import com.anypluspay.anypay.channel.request.UnifiedOrderRequest;
import com.anypluspay.anypay.channel.response.ChannelResponse;
import org.springframework.stereotype.Service;

@Service
public class TestUnifiedOrderService implements UnifiedOrderService {
    @Override
    public ChannelResponse unifiedOrder(UnifiedOrderRequest request) {
        return null;
    }
}
