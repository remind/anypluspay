package com.anypluspay.admin.dao;

import com.anypluspay.admin.dao.convertor.order.BizOrderConvertor;
import com.anypluspay.admin.dao.convertor.order.FundInOrderConvertor;
import com.anypluspay.admin.dao.convertor.order.InstOrderConvertor;
import com.anypluspay.admin.dao.convertor.order.InstProcessOrderConvertor;
import com.anypluspay.admin.model.order.BizOrderDto;
import com.anypluspay.admin.model.order.FundInOrderDto;
import com.anypluspay.admin.model.order.InstOrderDto;
import com.anypluspay.admin.model.order.InstProcessOrderDto;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.FundInOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.InstProcessOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.BizOrderMapper;
import com.anypluspay.channel.infra.persistence.mapper.FundInOrderMapper;
import com.anypluspay.channel.infra.persistence.mapper.InstOrderMapper;
import com.anypluspay.channel.infra.persistence.mapper.InstProcessOrderMapper;
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
    private FundInOrderMapper fundInOrderMapper;

    @Autowired
    private FundInOrderConvertor fundInOrderConvertor;

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
        return bizOrderConvertor.toEntity(bizOrderDO);
    }

    public BizOrderDto getBizOrderByRequestId(String requestId) {
        LambdaQueryWrapper<BizOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(BizOrderDO::getRequestId, requestId);
        return bizOrderConvertor.toEntity(bizOrderMapper.selectOne(queryWrapper));
    }

    public FundInOrderDto getFundInOrderByOrderId(String orderId) {
        FundInOrderDO fundInOrderDO = fundInOrderMapper.selectById(orderId);
        return fundInOrderConvertor.convert(fundInOrderDO);
    }

    public List<InstOrderDto> getInstOrderByBizOrderId(String bizOrderId) {
        LambdaQueryWrapper<InstOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(InstOrderDO::getBizOrderId, bizOrderId);
        return instOrderConvertor.toEntity(instOrderMapper.selectList(queryWrapper));
    }

    public List<InstProcessOrderDto> getInstProcessOrderByInstOrderId(List<String> instOrderIds) {
        LambdaQueryWrapper<InstProcessOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(InstProcessOrderDO::getInstOrderId, instOrderIds);
        return instProcessOrderConvertor.toEntity(instProcessOrderMapper.selectList(queryWrapper));
    }
}
