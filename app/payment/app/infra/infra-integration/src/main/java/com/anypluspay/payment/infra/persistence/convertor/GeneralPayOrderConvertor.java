package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.payment.domain.payorder.GeneralPayOrder;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.GeneralPayOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2025/2/23
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface GeneralPayOrderConvertor extends ReadWriteConvertor<GeneralPayOrder, GeneralPayOrderDO>  {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(payOrderDO.getAmount(), payOrderDO.getCurrencyCode()))")
    GeneralPayOrder toEntity(GeneralPayOrderDO payOrderDO);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(generalPayOrder.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(generalPayOrder.getAmount()))")
    GeneralPayOrderDO toDO(GeneralPayOrder generalPayOrder);
}
