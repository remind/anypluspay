package com.anypluspay.admin.dao.convertor.channel;

import com.anypluspay.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.model.channel.ChannelApiDto;
import com.anypluspay.admin.model.request.ChannelApiRequest;
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
