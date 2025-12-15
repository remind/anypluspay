package com.anypluspay.anypay.infra.persistence.trade.convertor;

import com.anypluspay.anypay.infra.persistence.dataobject.AcquiringOrderDO;
import com.anypluspay.anypay.trade.AcquiringOrder;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2025/5/17
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface AcquiringOrderDalConvertor extends ReadWriteConvertor<AcquiringOrder, AcquiringOrderDO> {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(doObject.getAmount(), doObject.getCurrencyCode()))")
    AcquiringOrder toEntity(AcquiringOrderDO doObject);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(entityObject.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(entityObject.getAmount()))")
    AcquiringOrderDO toDO(AcquiringOrder entityObject);
}
