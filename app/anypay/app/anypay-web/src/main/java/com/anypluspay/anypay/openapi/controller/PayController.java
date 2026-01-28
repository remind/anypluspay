package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.channel.ChannelRequestProxy;
import com.anypluspay.anypay.channel.request.ChannelUnifiedOrderRequest;
import com.anypluspay.anypay.channel.response.ChannelResponse;
import com.anypluspay.anypay.domain.pay.PayMethod;
import com.anypluspay.anypay.domain.pay.repository.PayMethodRepository;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.openapi.request.PaySubmitRequest;
import com.anypluspay.anypay.openapi.response.PaySubmitResponse;
import com.anypluspay.anypay.types.trade.TraderOrderStatus;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2026/1/28
 */
@RestController
@RequestMapping("/openapi/pay")
@Validated
public class PayController {

    @Resource
    private TradeOrderRepository tradeOrderRepository;

    @Resource
    private PayMethodRepository payMethodRepository;

    @Resource
    private ChannelRequestProxy channelRequestProxy;

    @PostMapping("/submit")
    public ResponseResult<PaySubmitResponse> submit(@RequestBody @Validated PaySubmitRequest request) {
        TradeOrder tradeOrder;
        if (request.getTradeId() != null) {
            tradeOrder = tradeOrderRepository.load(request.getTradeId());
        } else if (request.getOutTradeNo() != null) {
            tradeOrder = tradeOrderRepository.loadByOutTradeNo(request.getOutTradeNo());
        } else {
            throw new BizException("交易单号和外部交易单号至少传一个");
        }
        Assert.isTrue(tradeOrder != null, "订单不存在");
        Assert.isTrue(tradeOrder.getStatus() == TraderOrderStatus.INIT, "订单状态不是待支付");

        PayMethod payMethod = payMethodRepository.load(request.getPayMethod());
        Assert.isTrue(payMethod != null, "支付方式不存在");

        ChannelUnifiedOrderRequest channelUnifiedOrderRequest = new ChannelUnifiedOrderRequest();
        channelUnifiedOrderRequest.setChannelRequestNo(tradeOrder.getTradeId());
        channelUnifiedOrderRequest.setAmount(tradeOrder.getAmount());
        ChannelResponse channelResponse = channelRequestProxy.unifiedOrder(payMethod.getChannelCode(), channelUnifiedOrderRequest);

        return ResponseResult.success(null);
    }
}
