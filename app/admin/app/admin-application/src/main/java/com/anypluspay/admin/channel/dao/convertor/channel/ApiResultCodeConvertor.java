package com.anypluspay.admin.channel.dao.convertor.channel;

import com.anypluspay.admin.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.channel.dao.convertor.ConvertorUtils;
import com.anypluspay.admin.channel.model.channel.ApiResultCodeDto;
import com.anypluspay.admin.channel.model.request.ApiResultCodeRequest;
import com.anypluspay.channel.infra.persistence.dataobject.ApiResultCodeDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/12/1
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface ApiResultCodeConvertor extends SimpleCrudConvertor<ApiResultCodeDto, ApiResultCodeRequest, ApiResultCodeDO> {

    @Mapping(target = "apiTypeName", expression = "java(ConvertorUtils.toApiTypeName(doObject.getApiType()))")
    @Mapping(target = "resultStatusName", expression = "java(ConvertorUtils.toInstOrderStatusName(doObject.getResultStatus()))")
    ApiResultCodeDto toDto(ApiResultCodeDO doObject);


}
