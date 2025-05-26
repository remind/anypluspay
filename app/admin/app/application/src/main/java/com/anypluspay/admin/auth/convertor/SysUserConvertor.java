package com.anypluspay.admin.auth.convertor;

import com.anypluspay.admin.auth.request.SysUserRequest;
import com.anypluspay.admin.auth.response.SysUserResponse;
import com.anypluspay.admin.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.infra.persistence.dataobject.SysUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/5/26
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface SysUserConvertor extends SimpleCrudConvertor<SysUserResponse, SysUserRequest, SysUserDO> {

    @Mapping(target = "statusName", expression = "java(ConvertorUtils.toSysUserStatusName(doObject.getStatus()))")
    SysUserResponse toDto(SysUserDO doObject);
}
