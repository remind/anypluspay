package com.anypluspay.admin.payment.controller;

import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.admin.payment.convertor.WithdrawOrderConvertor;
import com.anypluspay.admin.payment.query.WithdrawOrderQuery;
import com.anypluspay.admin.payment.response.WithdrawOrderResponse;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.infra.persistence.dataobject.WithdrawOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.WithdrawOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提现订单
 *
 * @author wxj
 * 2025/5/19
 */
@RestController
@RequestMapping("/payment/withdraw")
public class WithdrawOrderController extends AbstractController {

    @Autowired
    private WithdrawOrderMapper withdrawOrderMapper;

    @Autowired
    private WithdrawOrderConvertor withdrawOrderConvertor;


    /**
     * 列表分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<WithdrawOrderResponse>> list(WithdrawOrderQuery query) {
        LambdaQueryWrapper<WithdrawOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getPaymentId())) {
            queryWrapper.eq(WithdrawOrderDO::getPaymentId, query.getPaymentId());
        }
        queryWrapper.orderByDesc(WithdrawOrderDO::getGmtCreate);
        IPage<WithdrawOrderDO> page = getIPage(query);
        return ResponseResult.success(withdrawOrderConvertor.toEntity(withdrawOrderMapper.selectPage(page, queryWrapper)));
    }
}
