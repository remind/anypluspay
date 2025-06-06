package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.payment.domain.trade.withdraw.WithdrawOrder;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.WithdrawOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2025/5/15
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface WithdrawOrderDalConvertor extends ReadWriteConvertor<WithdrawOrder, WithdrawOrderDO> {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(doObject.getAmount(), doObject.getCurrencyCode()))")
    WithdrawOrder toEntity(WithdrawOrderDO doObject);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(entityObject.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(entityObject.getAmount()))")
    WithdrawOrderDO toDO(WithdrawOrder entityObject);
}