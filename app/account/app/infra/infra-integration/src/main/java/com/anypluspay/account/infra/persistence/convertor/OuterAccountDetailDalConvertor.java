package com.anypluspay.account.infra.persistence.convertor;

import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDetailDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.commons.lang.types.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author wxj
 * 2023/12/23
 */

@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface OuterAccountDetailDalConvertor extends ReadWriteConvertor<OuterAccountDetail, OuterAccountDetailDO> {
    @Mapping(target = "beforeBalance", expression = "java(toMoney(outerAccountDetailDO.getBeforeBalance(), outerAccountDetailDO.getCurrencyCode()))")
    @Mapping(target = "afterBalance", expression = "java(toMoney(outerAccountDetailDO.getAfterBalance(), outerAccountDetailDO.getCurrencyCode()))")
    @Mapping(target = "amount", expression = "java(toMoney(outerAccountDetailDO.getAmount(), outerAccountDetailDO.getCurrencyCode()))")
    @Override
    OuterAccountDetail toEntity(OuterAccountDetailDO outerAccountDetailDO);

    @Mapping(target = "currencyCode", expression = "java(outerAccountDetail.getBeforeBalance().getCurrency().getCurrencyCode())")
    @Mapping(target = "beforeBalance", expression = "java(outerAccountDetail.getBeforeBalance().getAmount())")
    @Mapping(target = "afterBalance", expression = "java(outerAccountDetail.getAfterBalance().getAmount())")
    @Mapping(target = "amount", expression = "java(outerAccountDetail.getAmount().getAmount())")
    @Override
    OuterAccountDetailDO toDO(OuterAccountDetail outerAccountDetail);

    default Money toMoney(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }
}
