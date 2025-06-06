package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.PayOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2025/2/23
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface PayOrderConvertor extends ReadWriteConvertor<PayOrder, PayOrderDO>  {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(payOrderDO.getAmount(), payOrderDO.getCurrencyCode()))")
    PayOrder toEntity(PayOrderDO payOrderDO);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(generalPayOrder.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(generalPayOrder.getAmount()))")
    PayOrderDO toDO(PayOrder generalPayOrder);
}
