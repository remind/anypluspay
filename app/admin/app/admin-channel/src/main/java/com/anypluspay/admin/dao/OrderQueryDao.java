package com.anypluspay.admin.dao;

import com.anypluspay.admin.dao.convertor.order.*;
import com.anypluspay.admin.dao.mapper.FundInOrderQueryMapper;
import com.anypluspay.admin.dao.mapper.RefundOrderQueryMapper;
import com.anypluspay.admin.model.order.*;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.InstProcessOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.RefundOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.BizOrderMapper;
import com.anypluspay.channel.infra.persistence.mapper.InstOrderMapper;
import com.anypluspay.channel.infra.persistence.mapper.InstProcessOrderMapper;
import com.anypluspay.channel.infra.persistence.mapper.RefundOrderMapper;
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
    private InstProcessOrderConvertor instProcessOrderConvertor;

    @Autowired
    private InstProcessOrderMapper instProcessOrderMapper;


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

    public List<InstProcessOrderDto> getInstProcessOrderByInstOrderId(List<String> instOrderIds) {
        LambdaQueryWrapper<InstProcessOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(InstProcessOrderDO::getInstOrderId, instOrderIds);
        queryWrapper.orderByAsc(InstProcessOrderDO::getGmtModified);
        return instProcessOrderConvertor.toDto(instProcessOrderMapper.selectList(queryWrapper));
    }
}
