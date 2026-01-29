package com.anypluspay.anypay.infra.persistence.payment.convertor;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.infra.persistence.dataobject.PayOrderDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2026/1/29
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface PayOrderDalConvertor extends ReadWriteConvertor<PayOrder, PayOrderDO> {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(doObject.getAmount(), doObject.getCurrency()))")
    PayOrder toEntity(PayOrderDO doObject);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(entityObject.getAmount()))")
    @Mapping(target = "currency", expression = "java(toCurrencyCode(entityObject.getAmount()))")
    PayOrderDO toDO(PayOrder entityObject);
}
