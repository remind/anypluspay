package com.anypluspay.admin.dao;

import com.anypluspay.admin.dao.convertor.order.*;
import com.anypluspay.admin.dao.mapper.FundInOrderQueryMapper;
import com.anypluspay.admin.dao.mapper.RefundOrderQueryMapper;
import com.anypluspay.admin.model.order.*;
import com.anypluspay.channel.infra.persistence.dataobject.*;
import com.anypluspay.channel.infra.persistence.mapper.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单查询
 * @author wxj
 * 2024/11/21
 */
@Service
public class OrderQueryDao extends AbstractDao {

    @Autowired
    private BizOrderMapper bizOrderMapper;

    @Autowired
    private BizOrderConvertor bizOrderConvertor;

    @Autowired
    private FundInOrderQueryMapper fundInOrderQueryMapper;

    @Autowired
    private FundInOrderConvertor fundInOrderConvertor;

    @Autowired
    private RefundOrderMapper refundOrderMapper;

    @Autowired
    private RefundOrderQueryMapper refundOrderQueryMapper;

    @Autowired
    private RefundOrderConvertor refundOrderConvertor;

    @Autowired
    private InstOrderConvertor instOrderConvertor;

    @Autowired
    private InstOrderMapper instOrderMapper;

    @Autowired
    private InstCommandOrderConvertor instCommandOrderConvertor;

    @Autowired
    private InstCommandOrderMapper instCommandOrderMapper;


    public BizOrderDto getBizOrderByOrderId(String orderId) {
        BizOrderDO bizOrderDO = bizOrderMapper.selectById(orderId);
        return bizOrderConvertor.toDto(bizOrderDO);
    }

    public BizOrderDto getOrigBizOrderByRefundOrderId(String orderId) {
        RefundOrderDO refundOrderDO =  refundOrderMapper.selectById(orderId);
        return getBizOrderByOrderId(refundOrderDO.getOrigOrderId());
    }

    public BizOrderDto getBizOrderByRequestId(String requestId) {
        LambdaQueryWrapper<BizOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(BizOrderDO::getRequestId, requestId);
        return bizOrderConvertor.toDto(bizOrderMapper.selectOne(queryWrapper));
    }

    public FundInOrderDto getFundInOrderByOrderId(String orderId) {
        FundInOrderDto fundInOrderDto = fundInOrderQueryMapper.selectByOrderId(orderId);
        return fundInOrderConvertor.fill(fundInOrderDto);
    }

    public List<RefundOrderDto> getRefundOrderByBizOrigOrderId(String origOrderId) {
        List<RefundOrderDto> refundOrders = refundOrderQueryMapper.selectByOrigOrder(origOrderId);
        refundOrders.forEach(refundOrderDto -> {
            refundOrderConvertor.fill(refundOrderDto);
        });
        return refundOrders;
    }

    public List<InstOrderDto> getInstOrderByBizOrderId(List<String> orderIds) {
        LambdaQueryWrapper<InstOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(InstOrderDO::getBizOrderId, orderIds);
        queryWrapper.orderByAsc(InstOrderDO::getGmtModified);
        return instOrderConvertor.toDto(instOrderMapper.selectList(queryWrapper));
    }

    public List<InstCommandOrderDto> getInstProcessOrderByInstOrderId(List<String> instOrderIds) {
        LambdaQueryWrapper<InstCommandOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(InstCommandOrderDO::getInstOrderId, instOrderIds);
        queryWrapper.orderByAsc(InstCommandOrderDO::getGmtModified);
        return instCommandOrderConvertor.toDto(instCommandOrderMapper.selectList(queryWrapper));
    }
}
