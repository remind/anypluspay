package com.anypluspay.admin.account.convertor;

import com.anypluspay.account.infra.persistence.dataobject.OuterSubAccountDO;
import com.anypluspay.admin.account.dto.OuterSubAccountDto;
import com.anypluspay.basis.convertor.SimpleQueryConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/1/15
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OuterSubAccountConvertor extends SimpleQueryConvertor<OuterSubAccountDto, OuterSubAccountDO> {
}
