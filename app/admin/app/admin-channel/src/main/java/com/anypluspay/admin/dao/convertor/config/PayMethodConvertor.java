package com.anypluspay.admin.dao.convertor.config;

import com.anypluspay.admin.dao.convertor.SimpleCrudConvertor;
import com.anypluspay.admin.model.channel.ApiResultCodeDto;
import com.anypluspay.admin.model.config.PayMethodDto;
import com.anypluspay.admin.model.request.ApiResultCodeRequest;
import com.anypluspay.admin.model.request.PayMethodRequest;
import com.anypluspay.channel.infra.persistence.dataobject.ApiResultCodeDO;
import com.anypluspay.channel.infra.persistence.dataobject.PayMethodDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2024/12/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PayMethodConvertor extends SimpleCrudConvertor<PayMethodDto, PayMethodRequest, PayMethodDO> {

}
