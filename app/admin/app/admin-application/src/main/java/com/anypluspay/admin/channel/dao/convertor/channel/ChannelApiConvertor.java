package com.anypluspay.admin.channel.dao.convertor.channel;

import com.anypluspay.admin.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.channel.model.channel.ChannelApiDto;
import com.anypluspay.admin.channel.model.request.ChannelApiRequest;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelApiDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/27
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChannelApiConvertor extends SimpleCrudConvertor<ChannelApiDto, ChannelApiRequest, ChannelApiDO> {


}
