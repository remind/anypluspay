package com.anypluspay.account.infra.persistence.convertor;

import com.anypluspay.account.domain.OuterSubAccount;
import com.anypluspay.account.infra.persistence.dataobject.OuterSubAccountDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2023/12/18
 */
@Mapper(componentModel = "spring")
public interface OuterSubAccountDalConvertor extends ReadWriteConvertor<OuterSubAccount, OuterSubAccountDO> {

    @Mapping(target = "availableBalance", expression = "java(toMoney(outerSubAccountDO.getAvailableBalance(), outerSubAccountDO.getCurrencyCode()))")
    @Mapping(target = "balance", expression = "java(toMoney(outerSubAccountDO.getBalance(), outerSubAccountDO.getCurrencyCode()))")
    @Override
    OuterSubAccount toEntity(OuterSubAccountDO outerSubAccountDO);

    @Mapping(target = "balance", expression = "java(outerSubAccount.getBalance().getAmount())")
    @Mapping(target = "availableBalance", expression = "java(outerSubAccount.getAvailableBalance().getAmount())")
    @Mapping(target = "currencyCode", expression = "java(outerSubAccount.getBalance().getCurrency().getCurrencyCode())")
    @Override
    OuterSubAccountDO toDO(OuterSubAccount outerSubAccount);

}

