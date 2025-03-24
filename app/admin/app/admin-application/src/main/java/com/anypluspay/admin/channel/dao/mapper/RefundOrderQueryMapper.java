package com.anypluspay.admin.channel.dao.mapper;

import com.anypluspay.admin.channel.model.order.RefundOrderDto;

import java.util.List;

/**
 * @author wxj
 * 2024/12/11
 */
public interface RefundOrderQueryMapper {

    List<RefundOrderDto> selectByOrigOrder(String origOrderId);
}
