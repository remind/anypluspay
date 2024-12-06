package com.anypluspay.channel.infra.persistence.convertor;

import com.anypluspay.channel.domain.channel.ChannelMaintain;
import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelMaintainDO;
import com.anypluspay.component.dal.PageConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/30
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface ChannelMaintainDalConvertor extends PageConvertor<ChannelMaintain, ChannelMaintainDO> {
}
