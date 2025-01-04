package com.anypluspay.account.infra.persistence.convertor;

/**
 * @author wxj
 * 2023/12/24
 */

import com.anypluspay.account.domain.InnerAccount;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.InnerAccountDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface InnerAccountDalConvertor extends ReadWriteConvertor<InnerAccount, InnerAccountDO> {

    @Mapping(target = "balance", expression = "java(toMoney(innerAccountDO.getBalance(), innerAccountDO.getCurrencyCode()))")
    @Override
    InnerAccount toEntity(InnerAccountDO innerAccountDO);

    @Mapping(target = "balance", expression = "java(account.getBalance().getAmount())")
    @Override
    InnerAccountDO toDO(InnerAccount account);
}
