package com.anypluspay.anypay.application.pay;

import com.anypluspay.anypay.domain.channel.spi.response.ChannelOrderStatus;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelUnifiedOrderResponse;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.pay.service.PayOrderDomainService;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.domain.trade.service.TradeOrderDomainService;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import com.anypluspay.commons.exceptions.BizException;
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

    @Resource
    private TradeOrderDomainService tradeOrderDomainService;

    @Resource
    private PayOrderDomainService payOrderDomainService;

    public void processChannelResult(String tradeId, String payId, ChannelResponse channelResponse) {
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder tradeOrder = tradeOrderRepository.lock(tradeId);
            Assert.isTrue(tradeOrder != null, "订单不存在");
            Assert.isTrue(tradeOrder.getStatus() == TradeOrderStatus.WAIT_PAY, "订单状态不是待支付或支付中");
            PayOrder payOrder = payOrderRepository.lock(payId);
            if (StringUtils.isBlank(payOrder.getChannelResponseNo()) && StringUtils.isNotBlank(channelResponse.getChannelResponseNo())) {
                payOrder.setChannelResponseNo(channelResponse.getChannelResponseNo());
            }
            payOrder.setResultCode(channelResponse.getResultCode());
            payOrder.setResultMsg(channelResponse.getResultMsg());
            if (channelResponse instanceof ChannelUnifiedOrderResponse channelUnifiedOrderResponse) {
                if (StringUtils.isBlank(payOrder.getChannelParam()) && StringUtils.isNotBlank(channelUnifiedOrderResponse.getChannelParam())) {
                    payOrder.setChannelParam(channelUnifiedOrderResponse.getChannelParam());
                }
            }

            if (channelResponse.getStatus() == ChannelOrderStatus.SUCCESS) {
                payOrderDomainService.paySuccess(payOrder);
                tradeOrderDomainService.paySuccess(tradeOrder);
            } else if (channelResponse.getStatus() == ChannelOrderStatus.FAIL) {
                payOrderDomainService.payFail(payOrder);
            } else {
                payOrderDomainService.paying(payOrder);
            }
            tradeOrderRepository.reStore(tradeOrder);
            payOrderRepository.reStore(payOrder);
        });
    }

    public void processChannelResult(String channelCode, ChannelResponse channelResponse) {
        PayOrder payOrder;
        if (StringUtils.isNotBlank(channelResponse.getChannelRequestNo())) {
            payOrder = payOrderRepository.loadByChannelRequestNo(channelCode, channelResponse.getChannelRequestNo());
        } else if (StringUtils.isNotBlank(channelResponse.getChannelResponseNo())) {
            payOrder = payOrderRepository.loadByChannelResponseNo(channelCode, channelResponse.getChannelResponseNo());
        } else {
            throw new BizException("渠道请求单号和渠道响应单号至少传一个");
        }
        processChannelResult(payOrder.getTradeId(), payOrder.getPayId(), channelResponse);
    }
}
