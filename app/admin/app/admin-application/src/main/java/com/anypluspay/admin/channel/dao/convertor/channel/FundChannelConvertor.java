package com.anypluspay.admin.channel.dao.convertor.channel;

import com.anypluspay.admin.channel.dao.convertor.ConvertorUtils;
import com.anypluspay.admin.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.channel.model.channel.FundChannelDto;
import com.anypluspay.admin.channel.model.request.FundChannelRequest;
import com.anypluspay.channel.infra.persistence.dataobject.FundChannelDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/30
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface FundChannelConvertor extends SimpleCrudConvertor<FundChannelDto, FundChannelRequest, FundChannelDO> {

    @Mapping(target = "requestTypeName", expression = "java(ConvertorUtils.toRequestTypeName(doObject.getRequestType()))")
    FundChannelDto toDto(FundChannelDO doObject);

}
