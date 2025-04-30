package com.anypluspay.admin.trade.convertor;

import com.anypluspay.admin.trade.response.TradeOrderResponse;
import com.anypluspay.component.dal.PageConvertor;
import com.anypluspay.testtrade.infra.persistence.dataobject.TradeOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/4/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface TradeOrderConvertor extends PageConvertor<TradeOrderResponse, TradeOrderDO> {

    @Mapping(target = "status", expression = "java(ConvertorUtils.toTradeStatus(doObject.getStatus()))")
    @Override
    TradeOrderResponse toEntity(TradeOrderDO doObject);

}
