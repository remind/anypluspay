package com.anypluspay.admin.channel.dao.convertor.config;

import com.anypluspay.admin.channel.dao.convertor.ConvertorUtils;
import com.anypluspay.admin.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.channel.model.request.InstitutionRequest;
import com.anypluspay.admin.channel.model.config.InstitutionDto;
import com.anypluspay.channel.infra.persistence.dataobject.InstitutionDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/12/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface InstitutionConvertor extends SimpleCrudConvertor<InstitutionDto, InstitutionRequest, InstitutionDO>  {

    @Mapping(target = "instAbilityName", expression = "java(ConvertorUtils.toInstAbilityName(doObject.getInstAbility()))")
    InstitutionDto toDto(InstitutionDO doObject);

}
