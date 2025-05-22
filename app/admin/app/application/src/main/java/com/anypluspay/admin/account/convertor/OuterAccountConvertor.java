package com.anypluspay.admin.account.convertor;

import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDO;
import com.anypluspay.admin.account.model.dto.OuterAccountDto;
import com.anypluspay.admin.basis.convertor.SimpleQueryConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/1/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OuterAccountConvertor extends SimpleQueryConvertor<OuterAccountDto, OuterAccountDO> {

    @Mapping(target = "balanceDirectionName", expression = "java(ConvertorUtils.toBalanceDirectionName(doObject.getBalanceDirection()))")
    @Mapping(target = "denyStatusName", expression = "java(ConvertorUtils.toDenyStatusName(doObject.getDenyStatus()))")
    @Mapping(target = "accountAttributeName", expression = "java(ConvertorUtils.toAccountAttributeName(doObject.getAccountAttribute()))")
    @Override
    OuterAccountDto toDto(OuterAccountDO doObject);
}
