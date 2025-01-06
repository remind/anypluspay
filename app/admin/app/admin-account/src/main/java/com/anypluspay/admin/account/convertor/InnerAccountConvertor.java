package com.anypluspay.admin.account.convertor;

import com.anypluspay.account.infra.persistence.dataobject.InnerAccountDO;
import com.anypluspay.admin.account.dto.InnerAccountDto;
import com.anypluspay.admin.account.request.InnerAccountRequest;
import com.anypluspay.basis.convertor.SimpleCrudConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/1/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface InnerAccountConvertor extends SimpleCrudConvertor<InnerAccountDto, InnerAccountRequest, InnerAccountDO> {

}
