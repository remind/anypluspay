package com.anypluspay.admin.basis.testtrade;

import com.anypluspay.admin.basis.testtrade.convertor.PayOrderConvertor;
import com.anypluspay.admin.basis.testtrade.convertor.TradeOrderConvertor;
import com.anypluspay.admin.basis.testtrade.query.TradeQuery;
import com.anypluspay.admin.basis.testtrade.response.PayOrderResponse;
import com.anypluspay.admin.basis.testtrade.response.TradeOrderResponse;
import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.testtrade.infra.persistence.dataobject.PayOrderDO;
import com.anypluspay.testtrade.infra.persistence.dataobject.TradeOrderDO;
import com.anypluspay.testtrade.infra.persistence.mapper.PayOrderMapper;
import com.anypluspay.testtrade.infra.persistence.mapper.TradeOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试交易
 *
 * @author wxj
 * 2025/4/21
 */
@RestController
@RequestMapping("/test-trade")
public class TestTradeController extends AbstractController {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Autowired
    private TradeOrderConvertor tradeOrderConvertor;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private PayOrderConvertor payOrderConvertor;

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
     * 支付列表
     *
     * @param tradeId 查询参数
     * @return 查询结果
     */
    @GetMapping("/pay-list")
    public ResponseResult<List<PayOrderResponse>> payList(@NotBlank String tradeId) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrderDO::getTradeId, tradeId);
        queryWrapper.orderByDesc(PayOrderDO::getGmtCreate);
        return ResponseResult.success(payOrderConvertor.toEntity(payOrderMapper.selectList(queryWrapper)));
    }
}
