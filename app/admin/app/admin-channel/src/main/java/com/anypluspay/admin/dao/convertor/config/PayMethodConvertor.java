package com.anypluspay.admin.dao.convertor.config;

import com.anypluspay.basis.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.model.config.PayMethodDto;
import com.anypluspay.admin.model.request.PayMethodRequest;
import com.anypluspay.channel.infra.persistence.dataobject.PayMethodDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/12/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PayMethodConvertor extends SimpleCrudConvertor<PayMethodDto, PayMethodRequest, PayMethodDO> {

}
