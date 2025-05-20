package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.payment.domain.process.PayProcess;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.PayProcessDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2025/2/23
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface PayProcessConvertor extends ReadWriteConvertor<PayProcess, PayProcessDO>  {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(payOrderDO.getAmount(), payOrderDO.getCurrencyCode()))")
    PayProcess toEntity(PayProcessDO payOrderDO);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(generalPayOrder.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(generalPayOrder.getAmount()))")
    PayProcessDO toDO(PayProcess generalPayOrder);
}
