package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.application.trade.TradeService;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.openapi.builder.TradeBuilder;
import com.anypluspay.anypay.openapi.request.InstantCreateOrderRequest;
import com.anypluspay.anypay.openapi.response.TradeOrderResponse;
import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.ResponseResult;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 交易
 *
 * @author wxj
 * 2026/1/27
 */
@RestController
@RequestMapping("/openapi/trade")
@Validated
public class TradeController {

    @Resource
    private TradeBuilder tradeBuilder;

    @Resource
    private TradeOrderRepository tradeOrderRepository;

    @Resource
    private TradeService tradeService;

    @Resource
    private TransactionTemplate transactionTemplate;

    /**
     * 创建订单
     *
     * @param request 订单参数
     * @return
     */
    @PostMapping("/create")
    public ResponseResult<String> create(@RequestBody @Validated InstantCreateOrderRequest request) {
        TradeOrder tradeOrder = tradeBuilder.buildTradeOrder(request, TradeType.INSTANT_ACQUIRING);
        transactionTemplate.executeWithoutResult(status -> {
            tradeOrderRepository.store(tradeOrder);
        });
        return ResponseResult.success(tradeOrder.getTradeId());
    }

    /**
     * 查询订单
     *
     * @param tradeId    交易号
     * @param outTradeNo 外部交易号
     * @return
     */
    @GetMapping("/query")
    public ResponseResult<TradeOrderResponse> query(@RequestParam(required = false) String tradeId, @RequestParam(required = false) String outTradeNo) {
        TradeOrder tradeOrder = validateAndGet(tradeId, outTradeNo);
        return ResponseResult.success(tradeBuilder.buildTradeOrderResponse(tradeOrder));
    }

    /**
     * 关闭订单
     *
     * @param tradeId    交易号
     * @param outTradeNo 外部交易号
     * @return
     */
    @GetMapping("/close")
    public ResponseResult<String> close(@RequestParam(required = false) String tradeId, @RequestParam(required = false) String outTradeNo) {
        TradeOrder tradeOrder = validateAndGet(tradeId, outTradeNo);
        tradeService.close(tradeOrder.getTradeId());
        return ResponseResult.success();
    }

    /**
     * 根据交易号或外部交易号获取交易单
     *
     * @param tradeId
     * @param outTradeNo
     * @return
     */
    private TradeOrder validateAndGet(String tradeId, String outTradeNo) {
        TradeOrder tradeOrder;
        if (StringUtils.isNotBlank(tradeId)) {
            tradeOrder = tradeOrderRepository.load(tradeId);
        } else if (StringUtils.isNotBlank(outTradeNo)) {
            tradeOrder = tradeOrderRepository.loadByOutTradeNo(outTradeNo);
        } else {
            throw new BizException("参数错误");
        }
        Assert.isTrue(tradeOrder != null, "订单不存在");
        return tradeOrder;
    }
}
