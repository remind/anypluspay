package com.anypluspay.admin.payment.controller;

import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.admin.payment.convertor.DepositOrderConvertor;
import com.anypluspay.admin.payment.query.DepositOrderQuery;
import com.anypluspay.admin.payment.response.DepositOrderResponse;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.infra.persistence.dataobject.DepositOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.DepositOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 充值订单
 * @author wxj
 * 2025/5/19
 */
@RestController
@RequestMapping("/payment/deposit")
public class DepositOrderController extends AbstractController {

    @Autowired
    private DepositOrderMapper depositOrderMapper;

    @Autowired
    private DepositOrderConvertor depositOrderConvertor;


    /**
     * 列表分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<DepositOrderResponse>> list(DepositOrderQuery query) {
        LambdaQueryWrapper<DepositOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getPaymentId())) {
            queryWrapper.eq(DepositOrderDO::getPaymentId, query.getPaymentId());
        }
        queryWrapper.orderByDesc(DepositOrderDO::getGmtCreate);
        IPage<DepositOrderDO> page = getIPage(query);
        return ResponseResult.success(depositOrderConvertor.toEntity(depositOrderMapper.selectPage(page, queryWrapper)));
    }
}
