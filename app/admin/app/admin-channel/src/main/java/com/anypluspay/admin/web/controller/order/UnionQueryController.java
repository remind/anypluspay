package com.anypluspay.admin.web.controller.order;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.anypluspay.admin.model.UnionQueryResult;
import com.anypluspay.admin.model.order.BizOrderDto;
import com.anypluspay.admin.model.order.InstOrderDto;
import com.anypluspay.admin.dao.OrderQueryDao;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.InstOrderMapper;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.commons.response.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 联合查询
 *
 * @author wxj
 * 2024/11/14
 */
@RestController
@RequestMapping("/union-query")
public class UnionQueryController {

    @Autowired
    private OrderQueryDao orderQueryDao;

    @Autowired
    private InstOrderMapper instOrderMapper;

    /**
     * 查询
     *
     * @param orderId   单号
     * @param orderType 单号类型
     * @return
     */
    @GetMapping("/query")
    public ResponseResult<UnionQueryResult> query(String orderId, String orderType) {
        UnionQueryResult result = new UnionQueryResult();
        BizOrderDto bizOrderDto = getBizOrder(orderId, orderType);
        Assert.notNull(bizOrderDto, "订单不存在");
        result.setBizOrder(bizOrderDto);
        if (bizOrderDto.getRequestType() == RequestType.FUND_IN) {
            result.setFundInOrder(orderQueryDao.getFundInOrderByOrderId(result.getBizOrder().getOrderId()));
        }
        result.setInstOrders(orderQueryDao.getInstOrderByBizOrderId(bizOrderDto.getOrderId()));
        if (CollectionUtil.isNotEmpty(result.getInstOrders())) {
            result.setInstProcessOrders(orderQueryDao.getInstProcessOrderByInstOrderId(
                    result.getInstOrders().stream().map(InstOrderDto::getInstOrderId).toList()));
        }
        return ResponseResult.success(result);
    }

    private BizOrderDto getBizOrder(String orderId, String orderType) {
        BizOrderDto bizOrder = null;
        if ("1".equals(orderType)) {
            // 渠道订单号
            bizOrder = orderQueryDao.getBizOrderByOrderId(orderId);
        } else if ("2".equals(orderType)) {
            // 请求号
            LambdaQueryWrapper<BizOrderDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BizOrderDO::getRequestId, orderId);
            bizOrder = orderQueryDao.getBizOrderByRequestId(orderId);
        } else if ("3".equals(orderType)) {
            // 机构订单号
            InstOrderDO instOrderDO = instOrderMapper.selectById(orderId);
            if (instOrderDO != null) {
                bizOrder = orderQueryDao.getBizOrderByOrderId(instOrderDO.getBizOrderId());
            }
        } else if ("4".equals(orderType)) {
            // 机构请求号
            LambdaQueryWrapper<InstOrderDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InstOrderDO::getInstRequestNo, orderId);
            InstOrderDO instOrderDO = instOrderMapper.selectOne(queryWrapper);
            if (instOrderDO != null) {
                bizOrder = orderQueryDao.getBizOrderByOrderId(instOrderDO.getBizOrderId());
            }
        }
        return bizOrder;
    }

}
