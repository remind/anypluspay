package com.anypluspay.admin.dao.convertor.channel;

import com.anypluspay.admin.dao.convertor.ConvertorUtils;
import com.anypluspay.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.model.channel.ChannelSupportInstDto;
import com.anypluspay.admin.model.request.ChannelSupportInstRequest;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelSupportInstDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/28
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface ChannelSupportInstConvertor extends SimpleCrudConvertor<ChannelSupportInstDto, ChannelSupportInstRequest, ChannelSupportInstDO> {

    @Mapping(target = "cardTypeName", expression = "java(ConvertorUtils.toCardTypeName(doObject.getCardType()))")
    ChannelSupportInstDto toDto(ChannelSupportInstDO doObject);

}
