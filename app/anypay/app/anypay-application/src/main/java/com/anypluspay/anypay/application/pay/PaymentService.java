package com.anypluspay.anypay.application.pay;

import com.anypluspay.anypay.domain.channel.spi.response.ChannelOrderStatus;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.types.common.PayOrderStatus;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 * @author wxj
 * 2026/1/29
 */
@Service
public class PaymentService {

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private TradeOrderRepository tradeOrderRepository;

    @Resource
    private PayOrderRepository payOrderRepository;

    public void processResult(String tradeId, String payId, ChannelResponse channelResponse) {
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder tradeOrder = tradeOrderRepository.lock(tradeId);
            Assert.isTrue(tradeOrder != null, "订单不存在");
            Assert.isTrue(tradeOrder.getStatus() == TradeOrderStatus.PAYING || tradeOrder.getStatus() == TradeOrderStatus.WAIT_PAY, "订单状态不是待支付或支付中");
            PayOrder payOrder = payOrderRepository.lock(payId);
            if (StringUtils.isBlank(payOrder.getChannelResponseNo()) && StringUtils.isNotBlank(channelResponse.getChannelParam())) {
                payOrder.setChannelResponseNo(channelResponse.getChannelResponseNo());
            }
            if (StringUtils.isNotBlank(channelResponse.getChannelParam())) {
                payOrder.setChannelParam(channelResponse.getChannelParam());
            }
            payOrder.setResultCode(channelResponse.getResultCode());
            payOrder.setResultMsg(channelResponse.getResultMsg());
            if (channelResponse.getStatus() == ChannelOrderStatus.SUCCESS) {
                payOrder.setStatus(PayOrderStatus.SUCCESS);
                tradeOrder.setStatus(TradeOrderStatus.SUCCESS);
            } else if (channelResponse.getStatus() == ChannelOrderStatus.FAIL) {
                payOrder.setStatus(PayOrderStatus.FAIL);
                tradeOrder.setStatus(TradeOrderStatus.WAIT_PAY);
            } else {
                payOrder.setStatus(PayOrderStatus.PAYING);
                tradeOrder.setStatus(TradeOrderStatus.PAYING);
            }
            tradeOrderRepository.reStore(tradeOrder);
            payOrderRepository.reStore(payOrder);
        });
    }
}
