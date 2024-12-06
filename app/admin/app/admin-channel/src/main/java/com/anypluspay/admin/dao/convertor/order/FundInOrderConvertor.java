package com.anypluspay.admin.dao.convertor.order;

import com.anypluspay.admin.model.order.FundInOrderDto;
import com.anypluspay.channel.infra.persistence.dataobject.FundInOrderDO;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FundInOrderConvertor extends BaseExpressionConvertor {

    @Mapping(target = "amount", expression = "java(toMoney(fundInOrderDO.getAmount(), fundInOrderDO.getCurrencyCode()))")
    FundInOrderDto convert(FundInOrderDO fundInOrderDO);


}
