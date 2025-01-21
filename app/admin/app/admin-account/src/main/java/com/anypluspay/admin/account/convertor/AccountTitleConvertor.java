package com.anypluspay.admin.account.convertor;

import com.anypluspay.account.infra.persistence.dataobject.AccountTitleDO;
import com.anypluspay.admin.account.model.dto.AccountTitleDto;
import com.anypluspay.basis.convertor.SimpleQueryConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/12/26
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface AccountTitleConvertor extends SimpleQueryConvertor<AccountTitleDto, AccountTitleDO> {

    @Mapping(target = "typeName", expression = "java(ConvertorUtils.toAccountTitleTypeName(doObject.getType()))")
    @Mapping(target = "balanceDirectionName", expression = "java(ConvertorUtils.toBalanceDirectionName(doObject.getBalanceDirection()))")
    @Mapping(target = "scopeName", expression = "java(ConvertorUtils.toScopeName(doObject.getScope()))")
    AccountTitleDto toDto(AccountTitleDO doObject);
}
