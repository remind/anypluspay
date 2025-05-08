package com.anypluspay.admin.trade.controller;

import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.admin.trade.convertor.PayOrderConvertor;
import com.anypluspay.admin.trade.convertor.TradeRefundOrderConvertor;
import com.anypluspay.admin.trade.convertor.TradeOrderConvertor;
import com.anypluspay.admin.trade.query.TradeQuery;
import com.anypluspay.admin.trade.response.TradeOrderResponse;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.testtrade.facade.TradeFacade;
import com.anypluspay.testtrade.facade.request.TradeRefundRequest;
import com.anypluspay.testtrade.facade.response.RefundResponse;
import com.anypluspay.testtrade.infra.persistence.dataobject.PayOrderDO;
import com.anypluspay.testtrade.infra.persistence.dataobject.RefundOrderDO;
import com.anypluspay.testtrade.infra.persistence.dataobject.TradeOrderDO;
import com.anypluspay.testtrade.infra.persistence.mapper.PayOrderMapper;
import com.anypluspay.testtrade.infra.persistence.mapper.RefundOrderMapper;
import com.anypluspay.testtrade.infra.persistence.mapper.TradeOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易订单
 *
 * @author wxj
 * 2025/4/21
 */
@RestController
@RequestMapping("/trade/order")
public class TradeOrderController extends AbstractController {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Autowired
    private TradeOrderConvertor tradeOrderConvertor;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private PayOrderConvertor payOrderConvertor;

    @Autowired
    private RefundOrderMapper refundOrderMapper;

    @Autowired
    private TradeRefundOrderConvertor tradeRefundOrderConvertor;

    @Autowired
    private TradeFacade tradeFacade;

    /**
     * 列表分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<TradeOrderResponse>> list(TradeQuery query) {
        LambdaQueryWrapper<TradeOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getId())) {
            queryWrapper.eq(TradeOrderDO::getId, query.getId());
        }
        queryWrapper.orderByDesc(TradeOrderDO::getGmtCreate);
        IPage<TradeOrderDO> page = getIPage(query);
        return ResponseResult.success(tradeOrderConvertor.toEntity(tradeOrderMapper.selectPage(page, queryWrapper)));
    }

    /**
     * 支付详情，返回支付和退款列表
     *
     * @param tradeId 查询参数
     * @return 查询结果
     */
    @GetMapping("/pay-detail")
    public ResponseResult<Map<String, Object>> payDetail(@NotBlank String tradeId) {

        Map<String, Object> result = new HashMap<>();
        LambdaQueryWrapper<PayOrderDO> payQueryWrapper = new LambdaQueryWrapper<>();
        payQueryWrapper.eq(PayOrderDO::getTradeId, tradeId);
        payQueryWrapper.orderByDesc(PayOrderDO::getGmtCreate);
        result.put("pay", payOrderConvertor.toEntity(payOrderMapper.selectList(payQueryWrapper)));

        LambdaQueryWrapper<RefundOrderDO> refundQueryWrapper = new LambdaQueryWrapper<>();
        refundQueryWrapper.eq(RefundOrderDO::getTradeId, tradeId);
        refundQueryWrapper.orderByDesc(RefundOrderDO::getGmtCreate);
        result.put("refund", tradeRefundOrderConvertor.toEntity(refundOrderMapper.selectList(refundQueryWrapper)));

        return ResponseResult.success(result);
    }

    /**
     * 退款
     *
     * @param request
     * @return
     */
    @PostMapping("/refund")
    public ResponseResult<String> refund(@RequestBody TradeRefundRequest request) {
        RefundResponse response = tradeFacade.refund(request);
        if (response.getStatus().equals("0")) {
            return ResponseResult.success("处理中");
        } else if (response.getStatus().equals("1")) {
            return ResponseResult.success("处理成功");
        } else {
            return ResponseResult.success(response.getMessage());
        }
    }
}
