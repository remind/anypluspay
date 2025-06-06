package com.anypluspay.admin.payment.controller;

import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.admin.payment.convertor.AcquiringOrderConvertor;
import com.anypluspay.admin.payment.query.trade.AcquiringOrderQuery;
import com.anypluspay.admin.payment.request.RefundRequest;
import com.anypluspay.admin.payment.response.trade.AcquiringOrderResponse;
import com.anypluspay.commons.lang.utils.StringUtil;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.facade.acquiring.AcquiringFacade;
import com.anypluspay.payment.facade.acquiring.TradeResponse;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundRequest;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundResponse;
import com.anypluspay.payment.infra.persistence.dataobject.AcquiringOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.AcquiringOrderMapper;
import com.anypluspay.payment.types.trade.AcquiringOrderStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 收单订单
 *
 * @author wxj
 * 2025/5/19
 */
@RestController
@RequestMapping("/payment/acquiring")
public class AcquiringOrderController extends AbstractController {

    @Autowired
    private AcquiringOrderMapper tradeOrderMapper;

    @Autowired
    private AcquiringOrderConvertor acquiringOrderConvertor;

    @Autowired
    private AcquiringFacade acquiringFacade;


    /**
     * 列表分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<AcquiringOrderResponse>> list(AcquiringOrderQuery query) {
        LambdaQueryWrapper<AcquiringOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getTradeId())) {
            queryWrapper.eq(AcquiringOrderDO::getTradeId, query.getTradeId());
        }
        if (StringUtils.isNotBlank(query.getRelationTradeId())) {
            queryWrapper.eq(AcquiringOrderDO::getRelationTradeId, query.getRelationTradeId());
        }
        queryWrapper.orderByDesc(AcquiringOrderDO::getGmtCreate);
        IPage<AcquiringOrderDO> page = getIPage(query);
        return ResponseResult.success(acquiringOrderConvertor.toEntity(tradeOrderMapper.selectPage(page, queryWrapper)));
    }

    /**
     * 退款
     *
     * @param request 退款请求参数
     * @return 退款结果
     */
    @PostMapping("/refund")
    public ResponseResult<String> refund(@RequestBody RefundRequest request) {
        TradeResponse tradeResponse = acquiringFacade.queryByTradeId(request.getTradeId());
        if (tradeResponse.getStatus().equals(AcquiringOrderStatus.SUCCESS.getCode())) {
            AcquiringRefundRequest acquiringRefundRequest = new AcquiringRefundRequest();
            acquiringRefundRequest.setOrigTradeId(tradeResponse.getTradeId());
            acquiringRefundRequest.setOutTradeNo(StringUtil.randomId());
            acquiringRefundRequest.setAmount(request.getAmount());
            AcquiringRefundResponse acquiringRefundResponse = acquiringFacade.refund(acquiringRefundRequest);
            if (acquiringRefundResponse.isSuccess()) {
                if (acquiringRefundResponse.getOrderStatus().equals(AcquiringOrderStatus.SUCCESS.getCode())) {
                    return ResponseResult.success("退款成功");
                } else if (acquiringRefundResponse.getOrderStatus().equals(AcquiringOrderStatus.INIT.getCode())
                        || acquiringRefundResponse.getOrderStatus().equals(AcquiringOrderStatus.PAYING.getCode())
                ) {
                    return ResponseResult.success("提交成功，处理中");
                } else {
                    return ResponseResult.fail("退款失败");
                }
            } else {
                return ResponseResult.fail(acquiringRefundResponse.getResultCode(), acquiringRefundResponse.getResultMsg());
            }
        }
        return ResponseResult.fail("订单不是成功状态");
    }
}
