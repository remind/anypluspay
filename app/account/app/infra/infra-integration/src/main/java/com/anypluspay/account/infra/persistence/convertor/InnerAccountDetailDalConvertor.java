package com.anypluspay.account.infra.persistence.convertor;

import com.anypluspay.account.domain.detail.InnerAccountDetail;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.InnerAccountDetailDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.commons.lang.types.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author wxj
 * 2023/12/25
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface InnerAccountDetailDalConvertor extends ReadWriteConvertor<InnerAccountDetail, InnerAccountDetailDO> {
    @Mapping(target = "beforeBalance", expression = "java(toMoney(innerAccountDetailDO.getBeforeBalance(), innerAccountDetailDO.getCurrencyCode()))")
    @Mapping(target = "afterBalance", expression = "java(toMoney(innerAccountDetailDO.getAfterBalance(), innerAccountDetailDO.getCurrencyCode()))")
    @Mapping(target = "amount", expression = "java(toMoney(innerAccountDetailDO.getAmount(), innerAccountDetailDO.getCurrencyCode()))")
    @Override
    InnerAccountDetail toEntity(InnerAccountDetailDO innerAccountDetailDO);

    @Mapping(target = "currencyCode", expression = "java(innerAccountDetail.getBeforeBalance().getCurrency().getCurrencyCode())")
    @Mapping(target = "beforeBalance", expression = "java(innerAccountDetail.getBeforeBalance().getAmount())")
    @Mapping(target = "afterBalance", expression = "java(innerAccountDetail.getAfterBalance().getAmount())")
    @Mapping(target = "amount", expression = "java(innerAccountDetail.getAmount().getAmount())")
    @Override
    InnerAccountDetailDO toDO(InnerAccountDetail innerAccountDetail);

    default Money toMoney(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }
}

