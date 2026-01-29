package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.application.pay.PaymentService;
import com.anypluspay.anypay.domain.channel.ChannelRequestService;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelResponse;
import com.anypluspay.anypay.domain.pay.PayMethod;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayMethodRepository;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.openapi.builder.PayOrderBuilder;
import com.anypluspay.anypay.openapi.request.PaySubmitRequest;
import com.anypluspay.anypay.openapi.response.PaySubmitResponse;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
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
 * 支付接口
 *
 * @author wxj
 * 2026/1/28
 */
@RestController
@RequestMapping("/openapi/pay")
@Validated
public class PayController {

    @Resource
    private PayOrderBuilder payOrderBuilder;

    @Resource
    private TradeOrderRepository tradeOrderRepository;

    @Resource
    private PayOrderRepository payOrderRepository;

    @Resource
    private PayMethodRepository payMethodRepository;

    @Resource
    private ChannelRequestService channelRequestService;

    @Resource
    private PaymentService paymentService;

    /**
     * 提交支付
     *
     * @param request
     * @return
     */
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
        Assert.isTrue(tradeOrder.getStatus() == TradeOrderStatus.WAIT_PAY, "订单状态不是待支付");

        PayMethod payMethod = payMethodRepository.load(request.getPayMethod());
        Assert.isTrue(payMethod != null, "支付方式不存在");
        PayOrder payOrder = payOrderBuilder.build(request, payMethod, tradeOrder);
        payOrderRepository.store(payOrder);
        ChannelResponse channelResponse = channelRequestService.unifiedOrder(tradeOrder, payOrder);
        paymentService.processResult(tradeOrder.getTradeId(), payOrder.getPayId(), channelResponse);
        return ResponseResult.success(buildPaySubmitResponse(payOrder.getPayId()));
    }

    private PaySubmitResponse buildPaySubmitResponse(String payOrderId) {
        PayOrder payOrder = payOrderRepository.load(payOrderId);
        TradeOrder tradeOrder = tradeOrderRepository.load(payOrder.getTradeId());
        PaySubmitResponse response = new PaySubmitResponse();
        response.setTradeId(payOrder.getTradeId());
        response.setOutTradeNo(tradeOrder.getOutTradeNo());
        response.setPayMethod(payOrder.getPayMethod());
        response.setStatus(payOrder.getStatus().getDisplayName());
        response.setResultCode(payOrder.getResultCode());
        response.setResultMsg(payOrder.getResultMsg());
        response.setChannelParam(payOrder.getChannelParam());
        return response;
    }
}
