package com.anypluspay.anypay.domain.pay.service;

import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2026/1/29
 */
@Service
public class PayService {


    private TradeOrderRepository tradeOrderRepository;

    private PayOrderRepository payOrderRepository;

    public void process(ChannelResponse channelResponse) {

    }

}
