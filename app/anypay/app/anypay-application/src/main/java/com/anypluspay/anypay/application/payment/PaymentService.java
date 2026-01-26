package com.anypluspay.anypay.application.payment;

import com.anypluspay.anypay.channel.ChannelRequestProxy;
import com.anypluspay.anypay.domain.pay.PayWay;
import com.anypluspay.anypay.domain.pay.repository.PayWayRepository;
import com.anypluspay.anypay.types.payment.UnifiedOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 支付服务
 *
 * @author wxj
 * 2026/1/26
 */
@Service
public class PaymentService {

    @Autowired
    private ChannelRequestProxy channelRequestProxy;

    @Autowired
    private PayWayRepository payWayRepository;

    public String unifiedOrder(UnifiedOrderRequest request) {
        PayWay payWay = payWayRepository.load(request.getPayWayCode());
        Assert.notNull(payWay, "支付方式不存在");
        return "";
    }
}
