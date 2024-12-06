package com.anypluspay.channel.infra.persistence.convertor;

import com.anypluspay.channel.domain.channel.api.ApiRequestNoMode;
import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.ApiRequestNoModeDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/9/18
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface ApiRequestNoModeDalConvertor extends ReadWriteConvertor<ApiRequestNoMode, ApiRequestNoModeDO> {
}
