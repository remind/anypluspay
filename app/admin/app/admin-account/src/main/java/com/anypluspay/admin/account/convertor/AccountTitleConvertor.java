package com.anypluspay.admin.account.convertor;

import com.anypluspay.account.infra.persistence.dataobject.AccountTitleDO;
import com.anypluspay.admin.account.dto.AccountTitleDto;
import com.anypluspay.basis.convertor.SimpleQueryConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/12/26
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface AccountTitleConvertor extends SimpleQueryConvertor<AccountTitleDto, AccountTitleDO> {
}
