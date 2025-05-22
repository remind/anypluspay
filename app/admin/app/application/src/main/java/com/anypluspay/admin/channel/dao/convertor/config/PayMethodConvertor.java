package com.anypluspay.admin.channel.dao.convertor.config;

import com.anypluspay.admin.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.channel.model.config.PayMethodDto;
import com.anypluspay.admin.channel.model.request.PayMethodRequest;
import com.anypluspay.channel.infra.persistence.dataobject.PayModelDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/12/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PayMethodConvertor extends SimpleCrudConvertor<PayMethodDto, PayMethodRequest, PayModelDO> {

}
