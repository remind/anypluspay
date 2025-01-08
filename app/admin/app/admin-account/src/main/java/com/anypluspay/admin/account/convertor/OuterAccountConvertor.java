package com.anypluspay.admin.account.convertor;

import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDO;
import com.anypluspay.admin.account.dto.OuterAccountDto;
import com.anypluspay.basis.convertor.SimpleQueryConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/1/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OuterAccountConvertor extends SimpleQueryConvertor<OuterAccountDto, OuterAccountDO> {
}
