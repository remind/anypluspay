package com.anypluspay.admin.dao.convertor.channel;

import com.anypluspay.admin.dao.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.model.channel.ChannelApiDto;
import com.anypluspay.admin.model.request.ChannelApiRequest;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelApiDO;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2024/11/27
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChannelApiConvertor extends SimpleCrudConvertor<ChannelApiDto, ChannelApiRequest, ChannelApiDO> {


}
