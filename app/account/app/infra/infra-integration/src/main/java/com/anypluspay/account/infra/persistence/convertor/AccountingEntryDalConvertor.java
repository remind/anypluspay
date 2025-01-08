package com.anypluspay.account.infra.persistence.convertor;

import com.anypluspay.account.domain.accounting.AccountingEntry;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.AccountingEntryDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2025/1/8
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface AccountingEntryDalConvertor extends ReadWriteConvertor<AccountingEntry, AccountingEntryDO> {

    @Mapping(target = "amount", expression = "java(toMoney(accountingEntryDO.getAmount(), accountingEntryDO.getCurrencyCode()))")
    AccountingEntry toEntity(AccountingEntryDO accountingEntryDO);

    @Mapping(target = "amount", expression = "java(accountingEntry.getAmount().getAmount())")
    @Mapping(target = "currencyCode", expression = "java(accountingEntry.getAmount().getCurrency().getCurrencyCode())")
    @Override
    AccountingEntryDO toDO(AccountingEntry accountingEntry);
}
