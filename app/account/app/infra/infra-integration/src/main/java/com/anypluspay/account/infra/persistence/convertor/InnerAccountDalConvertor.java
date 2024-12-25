package com.anypluspay.account.infra.persistence.convertor;

/**
 * @author wxj
 * 2023/12/24
 */

import com.anypluspay.account.domain.InnerAccount;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.InnerAccountDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.commons.lang.types.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Currency;

@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface InnerAccountDalConvertor extends ReadWriteConvertor<InnerAccount, InnerAccountDO> {

    @Mapping(target = "balance", expression = "java(toMoney(innerAccountDO.getBalance(), innerAccountDO.getCurrencyCode()))")
    @Override
    InnerAccount toEntity(InnerAccountDO innerAccountDO);

    @Mapping(target = "balance", expression = "java(account.getBalance().getAmount())")
    @Override
    InnerAccountDO toDO(InnerAccount account);

    default Money toMoney(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }
}
