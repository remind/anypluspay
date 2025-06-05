package com.anypluspay.channel.infra.persistence.convertor;

import com.anypluspay.channel.domain.channel.api.ChannelApiParam;
import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelApiParamDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/6/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface ChannelApiParamDalConvertor extends ReadWriteConvertor<ChannelApiParam, ChannelApiParamDO> {
}
