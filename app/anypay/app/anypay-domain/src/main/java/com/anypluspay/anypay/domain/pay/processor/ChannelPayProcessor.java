package com.anypluspay.anypay.domain.pay.processor;

import com.anypluspay.anypay.domain.channel.ChannelFactoryService;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelOrderStatus;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelUnifiedOrderResponse;
import com.anypluspay.anypay.domain.pay.PayMethod;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.types.common.PayOrderStatus;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2026/2/3
 */
@Service
public class ChannelPayProcessor implements PayProcessor {

    @Resource
    private ChannelFactoryService channelFactoryService;

    @Resource
    private PayOrderRepository payOrderRepository;

    @Override
    public void pay(TradeOrder tradeOrder, PayOrder payOrder, PayMethod payMethod) {
        ChannelUnifiedOrderResponse channelResponse = channelFactoryService.unifiedOrder(payMethod.getChannelCode()).create(tradeOrder, payOrder);
        payOrder.setChannelParam(channelResponse.getChannelParam());
        processChannelResult(payOrder, channelResponse);
    }

    @Override
    public void refund(TradeOrder tradeOrder, PayOrder payOrder, PayOrder originPayOrder) {
        ChannelResponse channelResponse = channelFactoryService.channelRefund(originPayOrder.getChannelCode()).apply(tradeOrder, payOrder, originPayOrder);
        processChannelResult(payOrder, channelResponse);
    }

    private void processChannelResult(PayOrder payOrder, ChannelResponse channelResponse) {
        if (StringUtils.isBlank(payOrder.getChannelResponseNo()) && StringUtils.isNotBlank(channelResponse.getChannelResponseNo())) {
            payOrder.setChannelResponseNo(channelResponse.getChannelResponseNo());
        }
        payOrder.setResultCode(channelResponse.getResultCode());
        payOrder.setResultMsg(channelResponse.getResultMsg());

        if (channelResponse.getStatus() == ChannelOrderStatus.FAIL) {
            payOrder.setStatus(PayOrderStatus.FAIL);
        } else if (channelResponse.getStatus() == ChannelOrderStatus.PAYING) {
            payOrder.setStatus(PayOrderStatus.PAYING);
        }
        payOrderRepository.reStore(payOrder);
    }
}
