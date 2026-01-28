package com.anypluspay.anypay.infra.persistence.trade.convertor;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.infra.persistence.dataobject.TradeOrderDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2026/1/28
 */
@Mapper(componentModel = "spring", uses = {TradeEnumsConvertor.class})
public interface TradeOrderDalConvertor extends ReadWriteConvertor<TradeOrder, TradeOrderDO> {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(doObject.getAmount(), doObject.getCurrency()))")
    TradeOrder toEntity(TradeOrderDO doObject);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(entityObject.getAmount()))")
    @Mapping(target = "currency", expression = "java(toCurrencyCode(entityObject.getAmount()))")
    TradeOrderDO toDO(TradeOrder entityObject);
}
