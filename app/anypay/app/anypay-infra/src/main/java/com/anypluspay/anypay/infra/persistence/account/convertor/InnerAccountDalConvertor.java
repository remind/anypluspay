package com.anypluspay.anypay.infra.persistence.account.convertor;

/**
 * @author wxj
 * 2023/12/24
 */

import com.anypluspay.anypay.domain.account.InnerAccount;
import com.anypluspay.anypay.infra.persistence.dataobject.InnerAccountDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AccountEnumsConvertor.class})
public interface InnerAccountDalConvertor extends ReadWriteConvertor<InnerAccount, InnerAccountDO> {

    @Mapping(target = "balance", expression = "java(toMoney(innerAccountDO.getBalance(), innerAccountDO.getCurrencyCode()))")
    @Override
    InnerAccount toEntity(InnerAccountDO innerAccountDO);

    @Mapping(target = "balance", expression = "java(account.getBalance().getAmount())")
    @Override
    InnerAccountDO toDO(InnerAccount account);
}
