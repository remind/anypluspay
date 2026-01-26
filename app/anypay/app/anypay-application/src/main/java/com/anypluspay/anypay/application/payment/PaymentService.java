package com.anypluspay.anypay.application.payment;

import com.anypluspay.anypay.channel.ChannelRequestProxy;
import com.anypluspay.anypay.types.payment.UnifiedOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2026/1/26
 */
@Service
public class PaymentService {

    @Autowired
    private ChannelRequestProxy channelRequestProxy;

    public String unifiedOrder(UnifiedOrderRequest request) {
        return "";
    }
}
