package com.anypluspay.admin.dao.mapper;

import com.anypluspay.admin.model.order.RefundOrderDto;

import java.util.List;

/**
 * @author wxj
 * 2024/12/11
 */
public interface RefundOrderQueryMapper {

    List<RefundOrderDto> selectByOrigOrder(String origOrderId);
}
