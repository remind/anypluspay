package com.anypluspay.admin.payment.convertor;

import com.anypluspay.admin.payment.response.trade.AcquiringOrderResponse;
import com.anypluspay.component.dal.PageConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.AcquiringOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/5/19
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface AcquiringOrderConvertor extends PageConvertor<AcquiringOrderResponse, AcquiringOrderDO> {

    @Mapping(target = "status", expression = "java(ConvertorUtils.toAcquiringStatus(doObject.getStatus()))")
    @Mapping(target = "tradeTypeName", expression = "java(ConvertorUtils.toTradeType(doObject.getTradeType()))")
    @Mapping(target = "amount", expression = "java(ConvertorUtils.toDisplayMoney(doObject.getAmount(), doObject.getCurrencyCode()))")
    @Override
    AcquiringOrderResponse toEntity(AcquiringOrderDO doObject);
}
