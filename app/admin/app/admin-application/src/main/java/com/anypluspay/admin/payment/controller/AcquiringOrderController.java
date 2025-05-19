package com.anypluspay.admin.payment.controller;

import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.admin.payment.convertor.AcquiringOrderConvertor;
import com.anypluspay.admin.payment.query.AcquiringOrderQuery;
import com.anypluspay.admin.payment.response.AcquiringOrderResponse;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.infra.persistence.dataobject.AcquiringOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.AcquiringOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收单订单
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


    /**
     * 列表分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<AcquiringOrderResponse>> list(AcquiringOrderQuery query) {
        LambdaQueryWrapper<AcquiringOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getPaymentId())) {
            queryWrapper.eq(AcquiringOrderDO::getPaymentId, query.getPaymentId());
        }
        if (StringUtils.isNotBlank(query.getRelationPaymentId())) {
            queryWrapper.eq(AcquiringOrderDO::getRelationPaymentId, query.getRelationPaymentId());
        }
        queryWrapper.orderByDesc(AcquiringOrderDO::getGmtCreate);
        IPage<AcquiringOrderDO> page = getIPage(query);
        return ResponseResult.success(acquiringOrderConvertor.toEntity(tradeOrderMapper.selectPage(page, queryWrapper)));
    }

}
