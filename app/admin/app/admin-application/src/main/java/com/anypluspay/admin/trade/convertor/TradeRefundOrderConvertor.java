package com.anypluspay.admin.trade.convertor;

import com.anypluspay.admin.trade.response.RefundOrderResponse;
import com.anypluspay.component.dal.PageConvertor;
import com.anypluspay.testtrade.infra.persistence.dataobject.RefundOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/4/30
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface TradeRefundOrderConvertor extends PageConvertor<RefundOrderResponse, RefundOrderDO> {

    @Mapping(target = "status", expression = "java(ConvertorUtils.toRefundStatus(doObject.getStatus()))")
    @Override
    RefundOrderResponse toEntity(RefundOrderDO doObject);
}
