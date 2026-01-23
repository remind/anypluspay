package com.anypluspay.anypay.infra.persistence.trade.convertor;

import com.anypluspay.anypay.infra.persistence.dataobject.DepositOrderDO;
import com.anypluspay.anypay.domain.trade.DepositOrder;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2025/5/14
 */
@Mapper(componentModel = "spring", uses = {TradeEnumsConvertor.class})
public interface DepositOrderDalConvertor extends ReadWriteConvertor<DepositOrder, DepositOrderDO> {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(doObject.getAmount(), doObject.getCurrencyCode()))")
    DepositOrder toEntity(DepositOrderDO doObject);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(entityObject.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(entityObject.getAmount()))")
    DepositOrderDO toDO(DepositOrder entityObject);
}
